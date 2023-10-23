#  Copyright (C) 2023 The Android Open Source Project
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

import collections
import logging as log
import re

import gn_target
import utils


class GnParser:
    """A parser with some cleverness for GN json desc files

      The main goals of this parser are:
      1) Deal with the fact that other build systems don't have an equivalent
         notion to GN's source_set. Conversely to Bazel's and Soong's filegroups,
         GN source_sets expect that dependencies, cflags and other source_set
         properties propagate up to the linker unit (static_library, executable or
         shared_library). This parser simulates the same behavior: when a
         source_set is encountered, some of its variables (cflags and such) are
         copied up to the dependent targets. This is to allow gen_xxx to create
         one filegroup for each source_set and then squash all the other flags
         onto the linker unit.
      2) Detect and special-case protobuf targets, figuring out the protoc-plugin
         being used.
      """

    def __init__(self, builtin_deps):
        self.builtin_deps = builtin_deps
        self.all_targets = {}
        self.java_sources = collections.defaultdict(set)
        self.aidl_local_include_dirs = set()
        self.java_actions = collections.defaultdict(set)

    def _get_response_file_contents(self, action_desc):
        # response_file_contents are formatted as:
        # ['--flags', '--flag=true && false'] and need to be formatted as:
        # '--flags --flag=\"true && false\"'
        flags = action_desc.get('response_file_contents', [])
        formatted_flags = []
        for flag in flags:
            if '=' in flag:
                key, val = flag.split('=')
                formatted_flags.append('%s=\\"%s\\"' % (key, val))
            else:
                formatted_flags.append(flag)

        return ' '.join(formatted_flags)

    def _is_java_group(self, type_, target_name):
        # Per https://chromium.googlesource.com/chromium/src/build/+/HEAD/android/docs/java_toolchain.md
        # java target names must end in "_java".
        # TODO: There are some other possible variations we might need to support.
        return type_ == 'group' and target_name.endswith('_java')

    def _get_arch(self, toolchain):
        if toolchain == '//build/toolchain/android:android_clang_x86':
            return 'android_x86'
        elif toolchain == '//build/toolchain/android:android_clang_x64':
            return 'android_x86_64'
        elif toolchain == '//build/toolchain/android:android_clang_arm':
            return 'android_arm'
        elif toolchain == '//build/toolchain/android:android_clang_arm64':
            return 'android_arm64'
        else:
            return 'host'

    def get_target(self, gn_target_name):
        """Returns a Target object from the fully qualified GN target name.

          get_target() requires that parse_gn_desc() has already been called.
          """
        # Run this every time as parse_gn_desc can be called at any time.
        for target in self.all_targets.values():
            target.finalize()

        return self.all_targets[utils.label_without_toolchain(gn_target_name)]

    def parse_gn_desc(self, gn_desc, gn_target_name, java_group_name=None, is_test_target=False):
        """Parses a gn desc tree and resolves all target dependencies.

            It bubbles up variables from source_set dependencies as described in the
            class-level comments.
            """
        # Use name without toolchain for targets to support targets built for
        # multiple archs.
        target_name = utils.label_without_toolchain(gn_target_name)
        desc = gn_desc[gn_target_name]
        type_ = desc['type']
        arch = self._get_arch(desc['toolchain'])

        if self._is_java_group(type_, target_name):
            java_group_name = target_name

        if is_test_target:
            target_name += utils.TESTING_SUFFIX

        target = self.all_targets.get(target_name)
        if target is None:
            target = gn_target.Target(target_name, type_)
            self.all_targets[target_name] = target

        if arch not in target.arch:
            target.arch[arch] = gn_target.Target.Arch()
        else:
            return target  # Target already processed.

        if target.name in self.builtin_deps:
            # return early, no need to parse any further as the module is a builtin.
            return target

        target.testonly = desc.get('testonly', False)

        proto_target_type, proto_desc = self.get_proto_target_type(gn_desc, gn_target_name)
        if proto_target_type is not None:
            target.type = 'proto_library'
            target.proto_plugin = proto_target_type
            target.proto_paths.update(self.get_proto_paths(proto_desc))
            target.proto_exports.update(self.get_proto_exports(proto_desc))
            target.proto_in_dir = self.get_proto_in_dir(proto_desc)
            for gn_proto_deps_name in proto_desc.get('deps', []):
                dep = self.parse_gn_desc(gn_desc, gn_proto_deps_name)
                target.deps.add(dep.name)
            target.arch[arch].sources.update(proto_desc.get('sources', []))
            assert (all(x.endswith('.proto') for x in target.arch[arch].sources))
        elif target.type == 'source_set':
            target.arch[arch].sources.update(desc.get('sources', []))
        elif target.is_linker_unit_type():
            target.arch[arch].sources.update(desc.get('sources', []))
        elif (desc.get("script", "") in utils.JAVA_BANNED_SCRIPTS
              or self._is_java_group(target.type, target.name)):
            # java_group identifies the group target generated by the android_library
            # or java_library template. A java_group must not be added as a
            # dependency, but sources are collected.
            log.debug('Found java target %s', target.name)
            if target.type == "action":
                # Convert java actions into java_group and keep the inputs for collection.
                target.inputs.update(desc.get('inputs', []))
            target.type = 'java_group'
        elif target.type in ['action', 'action_foreach']:
            target.arch[arch].inputs.update(desc.get('inputs', []))
            target.arch[arch].sources.update(desc.get('sources', []))
            outs = [re.sub('^//out/.+?/gen/', '', x) for x in desc['outputs']]
            target.arch[arch].outputs.update(outs)
            # While the arguments might differ, an action should always use the same script for every
            # architecture. (gen_android_bp's get_action_sanitizer actually relies on this fact.
            target.script = desc['script']
            target.arch[arch].args = desc['args']
            target.arch[arch].response_file_contents = self._get_response_file_contents(desc)
        elif target.type == 'copy':
            # TODO: copy rules are not currently implemented.
            pass

        # Default for 'public' is //* - all headers in 'sources' are public.
        # TODO(primiano): if a 'public' section is specified (even if empty), then
        # the rest of 'sources' is considered inaccessible by gn. Consider
        # emulating that, so that generated build files don't end up with overly
        # accessible headers.
        public_headers = [x for x in desc.get('public', []) if x != '*']
        target.public_headers.update(public_headers)

        target.arch[arch].cflags.update(desc.get('cflags', []) + desc.get('cflags_cc', []))
        target.libs.update(desc.get('libs', []))
        target.arch[arch].ldflags.update(desc.get('ldflags', []))
        target.arch[arch].defines.update(desc.get('defines', []))
        target.arch[arch].include_dirs.update(desc.get('include_dirs', []))
        target.output_name = desc.get('output_name', None)
        if "-frtti" in target.arch[arch].cflags:
            target.rtti = True

        # Recurse in dependencies.
        for gn_dep_name in desc.get('deps', []):
            dep = self.parse_gn_desc(gn_desc, gn_dep_name, java_group_name, is_test_target)
            if dep.type == 'proto_library':
                target.proto_deps.add(dep.name)
                target.transitive_proto_deps.add(dep.name)
                target.proto_paths.update(dep.proto_paths)
                target.transitive_proto_deps.update(dep.transitive_proto_deps)
            elif dep.type == 'group':
                target.update(dep, arch)  # Bubble up groups's cflags/ldflags etc.
            elif dep.type in ['action', 'action_foreach', 'copy']:
                if proto_target_type is None:
                    target.arch[arch].deps.add(dep.name)
            elif dep.is_linker_unit_type():
                target.arch[arch].deps.add(dep.name)
            elif dep.type == 'java_group':
                # Explicitly break dependency chain when a java_group is added.
                # Java sources are collected and eventually compiled as one large
                # java_library.
                pass

            if dep.type in ['static_library', 'source_set']:
                # Bubble up static_libs and source_set. Necessary, since soong does not propagate
                # static_libs up the build tree.
                # Source sets are later translated to static_libraries, so it makes sense
                # to reuse transitive_static_libs_deps.
                target.arch[arch].transitive_static_libs_deps.add(dep.name)

            if arch in dep.arch:
                target.arch[arch].transitive_static_libs_deps.update(
                    dep.arch[arch].transitive_static_libs_deps)
                target.arch[arch].deps.update(target.arch[arch].transitive_static_libs_deps)

            # Collect java sources. Java sources are kept inside the __compile_java target.
            # This target can be used for both host and target compilation; only add
            # the sources if they are destined for the target (i.e. they are a
            # dependency of the __dex target)
            # Note: this skips prebuilt java dependencies. These will have to be
            # added manually when building the jar.
            if target.name.endswith('__dex'):
                if dep.name.endswith('__compile_java'):
                    log.debug('Adding java sources for %s', dep.name)
                    java_srcs = [src for src in dep.inputs if utils.is_java_source(src)]
                    if not is_test_target:
                        # TODO(aymanm): Fix collecting sources for testing modules for java.
                        # Don't collect java source files for test targets.
                        # We only need a specific set of java sources which are hardcoded in gen_android_bp
                        self.java_sources[java_group_name].update(java_srcs)
            if dep.type in ["action"] and target.type == "java_group":
                # GN uses an action to compile aidl files. However, this is not needed in soong
                # as soong can directly have .aidl files in srcs. So adding .aidl files to the java_sources.
                # TODO: Find a better way/place to do this.
                if not is_test_target:
                    if '_aidl' in dep.name:
                        self.java_sources[java_group_name].update(dep.arch[arch].sources)
                        self.aidl_local_include_dirs.update(
                            utils.extract_includes_from_aidl_args(dep.arch[arch].args))
                    else:
                        # TODO(aymanm): Fix collecting actions for testing modules for java.
                        # Don't collect java actions for test targets.
                        self.java_actions[java_group_name].add(dep.name)
        return target

    def get_proto_exports(self, proto_desc):
        # exports in metadata will be available for source_set targets.
        metadata = proto_desc.get('metadata', {})
        return metadata.get('exports', [])

    def get_proto_paths(self, proto_desc):
        # import_dirs in metadata will be available for source_set targets.
        metadata = proto_desc.get('metadata', {})
        return metadata.get('import_dirs', [])

    def get_proto_in_dir(self, proto_desc):
        args = proto_desc.get('args')
        return re.sub('^\.\./\.\./', '', args[args.index('--proto-in-dir') + 1])

    def get_proto_target_type(self, gn_desc, gn_target_name):
        """ Checks if the target is a proto library and return the plugin.

            Returns:
                (None, None): if the target is not a proto library.
                (plugin, proto_desc) where |plugin| is 'proto' in the default (lite)
                case or 'protozero' or 'ipc' or 'descriptor'; |proto_desc| is the GN
                json desc of the target with the .proto sources (_gen target for
                non-descriptor types or the target itself for descriptor type).
            """
        parts = gn_target_name.split('(', 1)
        name = parts[0]
        toolchain = '(' + parts[1] if len(parts) > 1 else ''

        # Descriptor targets don't have a _gen target; instead we look for the
        # characteristic flag in the args of the target itself.
        desc = gn_desc.get(gn_target_name)
        if '--descriptor_set_out' in desc.get('args', []):
            return 'descriptor', desc

        # Source set proto targets have a non-empty proto_library_sources in the
        # metadata of the description.
        metadata = desc.get('metadata', {})
        if 'proto_library_sources' in metadata:
            return 'source_set', desc

        # In all other cases, we want to look at the _gen target as that has the
        # important information.
        gen_desc = gn_desc.get('%s_gen%s' % (name, toolchain))
        if gen_desc is None or gen_desc['type'] != 'action':
            return None, None
        if gen_desc['script'] != '//tools/protoc_wrapper/protoc_wrapper.py':
            return None, None
        plugin = 'proto'
        args = gen_desc.get('args', [])
        for arg in (arg for arg in args if arg.startswith('--plugin=')):
            # |arg| at this point looks like:
            #  --plugin=protoc-gen-plugin=gcc_like_host/protozero_plugin
            # or
            #  --plugin=protoc-gen-plugin=protozero_plugin
            plugin = arg.split('=')[-1].split('/')[-1].replace('_plugin', '')
        return plugin, gen_desc

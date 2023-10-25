# Copyright (C) 2022 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# A collection of utilities for extracting build rule information from GN
# projects.

import copy
import json
from typing import Set, Dict, List

import constants
import utils


class GnTarget:
    def __init__(self, name: str):
        super().__init__()
        self.name: str = name
        self.is_finalized = False
        self._variant_deps: Set[GnTarget] = set()
        # This field name is intentionally not named as `variant` to make sure that it is not
        # a variant attribute.
        self._all_variants: Dict[constants.Variant, GnTarget] = {}

    def populate_variant(self, desc: Dict[str, str | List[str]]):
        variant = utils.get_variant(desc['toolchain'])
        self.create_variant(variant)

    def add_dependency(self, variant: constants.Variant, target: 'GnTarget'):
        if isinstance(target, ActionGnTarget):
            self._add_action_target_dependency(variant, target)
        elif isinstance(target, GroupGnTarget):
            self._add_group_target_dependency(variant, target)
        elif isinstance(target, SourceSetGnTarget):
            self._add_source_set_target_dependency(variant, target)
        elif isinstance(target, _CppGnTarget):
            self._add_cpp_target_dependency(variant, target)
        elif isinstance(target, JavaGnTarget):
            self._add_java_target_dependency(variant, target)
        elif isinstance(target, ProtoGnTarget):
            self._add_proto_target_dependency(variant, target)
        elif isinstance(target, BuiltInGnTarget):
            self._add_builtin_target_dependency(variant, target)
        elif isinstance(target, IgnoredGnTarget):
            # IgnoredGnTargets are usually copy targets which we don't want to support for now.
            pass
        else:
            raise Exception("Unable to identify the type"
                            " of the target dependency, target name: %s, target type: %s" % (
                                target.name, type(target)))

    def _add_action_target_dependency(self, variant: constants.Variant, target: 'ActionGnTarget'):
        raise Exception(
            """Unable to add a dependency on target (%s) of type 
            (%s) for a dependee of name (%s) and type (%s)""" % (
                target.name, type(target), self.name, type(self)))

    def _add_cpp_target_dependency(self, variant: constants.Variant, target: '_CppGnTarget'):
        raise Exception(
            """Unable to add a dependency on target (%s) of type 
            (%s) for a dependee of name (%s) and type (%s)""" % (
                target.name, type(target), self.name, type(self)))

    def _add_source_set_target_dependency(self, variant: constants.Variant,
                                          target: 'SourceSetGnTarget'):
        raise Exception(
            """Unable to add a dependency on target (%s) of type 
            (%s) for a dependee of name (%s) and type (%s)""" % (
                target.name, type(target), self.name, type(self)))

    def _add_java_target_dependency(self, variant: constants.Variant, target: 'JavaGnTarget'):
        raise Exception(
            """Unable to add a dependency on target (%s) of type 
            (%s) for a dependee of name (%s) and type (%s)""" % (
                target.name, type(target), self.name, type(self)))

    def _add_proto_target_dependency(self, variant: constants.Variant, target: 'ProtoGnTarget'):
        raise Exception(
            """Unable to add a dependency on target (%s) of type 
            (%s) for a dependee of name (%s) and type (%s)""" % (
                target.name, type(target), self.name, type(self)))

    def _add_group_target_dependency(self, variant: constants.Variant, target: 'GroupGnTarget'):
        raise Exception(
            """Unable to add a dependency on target (%s) of type 
            (%s) for a dependee of name (%s) and type (%s)""" % (
                target.name, type(target), self.name, type(self)))

    def _add_builtin_target_dependency(self, variant: constants.Variant, target: 'BuiltInGnTarget'):
        self.get_deps(variant).add(target)

    def get_deps(self, variant: constants.Variant):
        return self._all_variants[variant]._variant_deps

    def device_supported(self):
        return any([name.value.startswith('android') for name in self._all_variants.keys()])

    def host_supported(self):
        return constants.Variant.HOST in self._all_variants.keys()

    def __repr__(self):
        return json.dumps({
            k: (list(sorted(v)) if isinstance(v, set) else v)
            for (k, v) in self.__dict__.items()
        },
            indent=4,
            sort_keys=True)

    def _finalize_set_attribute(self, key: str):
        # Target contains the intersection of arch-dependent properties
        getattr(self, key).update(set.intersection(*[getattr(variant, key) for variant in
                                                     self.get_variants().values()]))

        # Deduplicate variant-dependent properties
        for variant in self.get_variants().values():
            getattr(variant, key).difference_update(getattr(self, key))

    def _finalize_non_set_attribute(self, key: str):
        # Only when all the variant has the same non empty value, move the value to the target common
        val = getattr(list(self.get_variants().values())[0], key)
        if val and all([val == getattr(variant, key) for variant in self.get_variants().values()]):
            setattr(self, key, copy.deepcopy(val))

    def _finalize_attribute(self, key: str):
        val = getattr(self, key)
        if isinstance(val, set):
            self._finalize_set_attribute(key)
        elif isinstance(val, (list, str, bool)):
            self._finalize_non_set_attribute(key)
        elif isinstance(val, GnTarget):
            # Explicitly Ignore GnTarget attributes are those are just Variant targets.
            pass
        else:
            raise TypeError(f'Unsupported type: {type(val)}, key: {key}')

    def finalize(self):
        """
        Move common properties out of variant-dependent subobjects to the parent object.
        """

        if self.is_finalized:
            return
        self.is_finalized = True

        for key in self.__dict__.keys():
            if utils.is_variant_attribute(key):
                self._finalize_attribute(key)

    def get_target_name(self) -> str:
        return self.name[self.name.find(":") + 1:]

    def create_variant(self, variant: constants.Variant) -> None:
        """
        This method will create another instance of the same class for which it is executed on
        and added it to the dictionary of `_all_variant`. Say you have the
        following fields on a class:

        class_fields = {
          "field_a" = 1,
          "field_b" = "abc",
          "field_c" = Set(..),
          "_all_variants" = {}
        }

        After calling create_arch, the `class_fields` will look like the following:

        class_fields = {
          "field_a" = 1,
          "field_b" = "abc",
          "field_c" = Set(..),
          "_all_variants" = {
            ["variant_a"] = {
                "field_a" = 0,
                "field_b" = "",
                "field_c" = Set(),
            }
          }
        }

        :param variant: Type of the new variant according to constants.Variant
        """
        self._all_variants[variant] = type(self)(self.name)

    def get_variants(self) -> Dict[constants.Variant, 'GnTarget']:
        """ Returns a dict of variants """
        return {variant: val for variant, val in self._all_variants.items()}


class ActionGnTarget(GnTarget):
    def __init__(self, name: str):
        super().__init__(name)
        self.script: str = ""
        self._variant_inputs: Set[str] = set()
        self._variant_outputs: Set[str] = set()
        self._variant_args: List[str] = []
        self._variant_response_file_contents: str = ""

    def populate_variant(self, desc: Dict[str, str | List[str]]):
        super().populate_variant(desc)
        variant = utils.get_variant(desc["toolchain"])
        self.script = desc["script"]
        self.set_inputs(variant, desc.get("inputs", set()))
        self.set_outputs(variant, set([utils.remove_gen_path(output) for output in
                                       desc.get("outputs", set())]))
        self.set_args(variant, desc.get("args"))
        self.set_response_file_contents(variant, utils.escape_response_file_contents(
            desc.get("response_file_contents", [])))

    def _add_source_set_target_dependency(self, variant: constants.Variant,
                                          target: 'SourceSetGnTarget'):
        # Some GnAction can depend on sourceSet(eg: //base:anchor_functions_buildflags)
        self.get_deps(variant).add(target)

    def _add_action_target_dependency(self, variant: constants.Variant, target: 'ActionGnTarget'):
        self.get_deps(variant).add(target)

    def _add_java_target_dependency(self, variant: constants.Variant, target: 'JavaGnTarget'):
        pass

    def _add_group_target_dependency(self, variant: constants.Variant, target: 'GroupGnTarget'):
        self.get_deps(variant).add(target)

    def _add_cpp_target_dependency(self, variant: constants.Variant, target: '_CppGnTarget'):
        self.get_deps(variant).add(target)

    def get_inputs(self, variant: constants.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_inputs

    def set_inputs(self, variant: constants.Variant, inputs: Set[str]):
        self._all_variants[variant]._variant_inputs = inputs

    def get_outputs(self, variant: constants.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_outputs

    def set_outputs(self, variant: constants.Variant, outputs: Set[str]):
        self._all_variants[variant]._variant_outputs = outputs

    def get_args(self, variant: constants.Variant) -> List[str]:
        return self._all_variants[variant]._variant_args

    def set_args(self, variant: constants.Variant, args: List[str]):
        self._all_variants[variant]._variant_args = args

    def get_response_file_contents(self, variant: constants.Variant) -> str:
        return self._all_variants[variant]._variant_response_file_contents

    def set_response_file_contents(self, variant: constants.Variant, value: str):
        self._all_variants[variant]._variant_response_file_contents = value


class ProtoGnTarget(GnTarget):
    def __init__(self, name: str):
        super().__init__(name)
        self.proto_plugin: str = ""
        self.proto_in_dir: str = ""
        self._variant_sources: Set[str] = set()

    def populate_variant(self, desc: Dict[str, str | List[str]]):
        super().populate_variant(desc)
        variant = utils.get_variant(desc["toolchain"])
        self.proto_plugin = "proto"
        self.proto_in_dir = utils.get_proto_in_dir(desc["args"])
        self.set_sources(variant, desc.get("sources", set()))

    def get_sources(self, variant: constants.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_sources

    def set_sources(self, variant: constants.Variant, sources: Set[str]):
        self._all_variants[variant]._variant_sources = sources

    def _add_cpp_target_dependency(self, variant: constants.Variant, target: '_CppGnTarget'):
        self.get_deps(variant).add(target)

    def _add_source_set_target_dependency(self, variant: constants.Variant,
                                          target: 'SourceSetGnTarget'):
        self.get_deps(variant).add(target)


class JavaGnTarget(GnTarget):
    def __init__(self, name: str):
        super().__init__(name)

    def add_dependency(self, variant: constants.Variant, target: 'JavaGnTarget'):
        pass


class _CppGnTarget(GnTarget):
    """
    Represents a CPP GN Target. This class defines the blueprint for
    """

    def __init__(self, name: str):
        super().__init__(name)
        self.output_name: str = ""
        self._variant_sources: Set[str] = set()
        self._variant_cflags: Set[str] = set()
        self._variant_defines: Set[str] = set()
        self._variant_include_dirs: Set[str] = set()
        self._variant_transitive_static_libs_deps: Set[GnTarget] = set()
        self._variant_ldflags: Set[str] = set()
        self._variant_libs: Set[str] = set()
        self._variant_rtti: bool = False
        self._variant_transitive_proto_deps: Set[GnTarget] = set()

    def populate_variant(self, desc: Dict[str, str | List[str]]):
        super().populate_variant(desc)
        variant = utils.get_variant(desc["toolchain"])
        self.set_sources(variant, desc.get("sources", set()))
        self.set_cflags(variant,
                        set(
                            set(desc.get("cflags", set())) |
                            set(desc.get('cflags_cc', set()))
                        ))
        self.set_libs(variant, desc.get("libs", set()))
        self.set_ldflags(variant, desc.get("ldflags", set()))
        self.set_defines(variant, desc.get("defines", set()))
        self.set_include_dirs(variant, desc.get("include_dirs", set()))
        self.output_name = desc.get("output_name", "")
        self.set_rtti(variant, constants.CFLAG_RTTI in self.get_cflags(variant))

    def _add_action_target_dependency(self, variant: constants.Variant, target: ActionGnTarget):
        self.get_deps(variant).add(target)

    def _add_proto_target_dependency(self, variant: constants.Variant, target: ProtoGnTarget):
        self.get_transitive_proto_deps(variant).add(target)
        self.get_transitive_proto_deps(variant).update(self.get_transitive_proto_deps(variant))

    def _add_cpp_target_dependency(self, variant: constants.Variant, target: '_CppGnTarget'):
        self.get_transitive_static_libs_deps(variant).add(target)
        self.get_transitive_static_libs_deps(variant).update(
            target.get_transitive_static_libs_deps(variant))

    def _add_source_set_target_dependency(self, variant: constants.Variant,
                                          target: 'SourceSetGnTarget'):
        self._add_cpp_target_dependency(variant, target)

    def _add_group_target_dependency(self, variant: constants.Variant, target: 'GroupGnTarget'):
        for variant_key in ("_variant_cflags", "_variant_defines", "_variant_include_dirs",
                            "_variant_transitive_static_libs_deps",
                            "_variant_ldflags", "_variant_libs", "_variant_transitive_proto_deps"):
            getattr(self._all_variants[variant], variant_key).update(
                getattr(target._all_variants[variant], variant_key, set()))

    def _add_java_target_dependency(self, variant: constants.Variant, target: 'JavaGnTarget'):
        pass

    def get_cflags(self, variant: constants.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_cflags

    def set_cflags(self, variant: constants.Variant, cflags: Set[str]):
        self._all_variants[variant]._variant_cflags = set(cflags)

    def get_defines(self, variant: constants.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_defines

    def set_defines(self, variant: constants.Variant, defines: Set[str]):
        self._all_variants[variant]._variant_defines = set(defines)

    def get_include_dirs(self, variant: constants.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_include_dirs

    def set_include_dirs(self, variant: constants.Variant, include_dirs: Set[str]):
        self._all_variants[variant]._variant_include_dirs = set(include_dirs)

    def get_ldflags(self, variant: constants.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_ldflags

    def set_ldflags(self, variant: constants.Variant, ld_flags: Set[str]):
        self._all_variants[variant]._variant_ldflags = set(ld_flags)

    def set_rtti(self, variant: constants.Variant, rtti: bool):
        self._all_variants[variant]._variant_rtti = rtti

    def set_libs(self, variant: constants.Variant, libs: Set[str]):
        self._all_variants[variant]._variant_libs = set(libs)

    def get_sources(self, variant: constants.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_sources

    def set_sources(self, variant: constants.Variant, sources: Set[str]):
        self._all_variants[variant]._variant_sources = set(sources)

    def get_proto_deps(self, variant: constants.Variant) -> Set[GnTarget]:
        return self._all_variants[variant]._variant_proto_deps

    def get_transitive_proto_deps(self, variant: constants.Variant) -> Set[GnTarget]:
        return self._all_variants[variant]._variant_transitive_proto_deps

    def get_transitive_static_libs_deps(self, variant: constants.Variant) -> Set[GnTarget]:
        return self._all_variants[variant]._variant_transitive_static_libs_deps


class SourceSetGnTarget(_CppGnTarget):
    def __init__(self, name: str):
        super().__init__(name)


class CppStaticLibraryGnTarget(_CppGnTarget):
    def __init__(self, name: str):
        super().__init__(name)


class CppSharedLibraryGnTarget(_CppGnTarget):
    def __init__(self, name: str):
        super().__init__(name)


class ExecutableGnTarget(_CppGnTarget):
    def __init__(self, name: str):
        super().__init__(name)


class GroupGnTarget(_CppGnTarget):
    def __init__(self, name: str):
        super().__init__(name)


class BuiltInGnTarget(GnTarget):
    def __init__(self, name: str):
        super().__init__(name)


class IgnoredGnTarget(GnTarget):
    def __init__(self, name: str):
        super().__init__(name)

    def add_dependency(self, variant: constants.Variant, target: 'GnTarget'):
        pass

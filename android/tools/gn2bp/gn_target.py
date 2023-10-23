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
import utils


class Target:
    """
    Reperesents A GN target.

    Maked properties are propagated up the dependency chain when a
    source_set dependency is encountered.
    """

    class Arch():
        """
        Architecture-dependent properties
        """

        def __init__(self):
            self.sources = set()
            self.cflags = set()
            self.defines = set()
            self.include_dirs = set()
            self.deps = set()
            self.transitive_static_libs_deps = set()
            self.source_set_deps = set()
            self.ldflags = set()

            # These are valid only for type == 'action'
            self.inputs = set()
            self.outputs = set()
            self.args = []
            self.response_file_contents = ''

    def __init__(self, name, type):
        self.name = name  # e.g. //src/ipc:ipc

        VALID_TYPES = ('static_library', 'shared_library', 'executable', 'group',
                       'action', 'source_set', 'proto_library', 'copy', 'action_foreach')
        assert (type in VALID_TYPES)
        self.type = type
        self.testonly = False
        self.toolchain = None

        # These are valid only for type == proto_library.
        # This is typically: 'proto', 'protozero', 'ipc'.
        self.proto_plugin = None
        self.proto_paths = set()
        self.proto_exports = set()
        self.proto_in_dir = ""

        # TODO(primiano): consider whether the public section should be part of
        # bubbled-up sources.
        self.public_headers = set()  # 'public'

        # These are valid only for type == 'action'
        self.script = ''

        # These variables are propagated up when encountering a dependency
        # on a source_set target.
        self.libs = set()
        self.proto_deps = set()
        self.transitive_proto_deps = set()
        self.rtti = False

        # TODO: come up with a better way to only run this once.
        # is_finalized tracks whether finalize() was called on this target.
        self.is_finalized = False
        # 'common' is a pseudo-architecture used to store common architecture dependent properties (to
        # make handling of common vs architecture-specific arguments more consistent).
        self.arch = {'common': self.Arch()}

        # This is used to get the name/version of libcronet
        self.output_name = None

    # Properties to forward access to common arch.
    # TODO: delete these after the transition has been completed.
    @property
    def sources(self):
        return self.arch['common'].sources

    @sources.setter
    def sources(self, val):
        self.arch['common'].sources = val

    @property
    def inputs(self):
        return self.arch['common'].inputs

    @inputs.setter
    def inputs(self, val):
        self.arch['common'].inputs = val

    @property
    def outputs(self):
        return self.arch['common'].outputs

    @outputs.setter
    def outputs(self, val):
        self.arch['common'].outputs = val

    @property
    def args(self):
        return self.arch['common'].args

    @args.setter
    def args(self, val):
        self.arch['common'].args = val

    @property
    def response_file_contents(self):
        return self.arch['common'].response_file_contents

    @response_file_contents.setter
    def response_file_contents(self, val):
        self.arch['common'].response_file_contents = val

    @property
    def cflags(self):
        return self.arch['common'].cflags

    @property
    def defines(self):
        return self.arch['common'].defines

    @property
    def deps(self):
        return self.arch['common'].deps

    @property
    def include_dirs(self):
        return self.arch['common'].include_dirs

    @property
    def ldflags(self):
        return self.arch['common'].ldflags

    @property
    def source_set_deps(self):
        return self.arch['common'].source_set_deps

    def host_supported(self):
        return 'host' in self.arch

    def device_supported(self):
        return any([name.startswith('android') for name in self.arch.keys()])

    def is_linker_unit_type(self):
        return self.type in utils.LINKER_UNIT_TYPES

    def __lt__(self, other):
        if isinstance(other, self.__class__):
            return self.name < other.name
        raise TypeError(
            '\'<\' not supported between instances of \'%s\' and \'%s\'' %
            (type(self).__name__, type(other).__name__))

    def __repr__(self):
        return json.dumps({
            k: (list(sorted(v)) if isinstance(v, set) else v)
            for (k, v) in self.__dict__.items()
        },
            indent=4,
            sort_keys=True)

    def update(self, other, arch):
        for key in ('cflags', 'defines', 'deps', 'include_dirs', 'ldflags',
                    'source_set_deps', 'proto_deps', 'transitive_proto_deps',
                    'libs', 'proto_paths'):
            getattr(self, key).update(getattr(other, key, []))

        for key_in_arch in ('cflags', 'defines', 'include_dirs', 'source_set_deps', 'ldflags'):
            getattr(self.arch[arch], key_in_arch).update(getattr(other.arch[arch], key_in_arch, []))

    def get_archs(self):
        """ Returns a dict of archs without the common arch """
        return {arch: val for arch, val in self.arch.items() if arch != 'common'}

    def _finalize_set_attribute(self, key):
        # Target contains the intersection of arch-dependent properties
        getattr(self, key).update(set.intersection(*[getattr(arch, key) for arch in
                                                     self.get_archs().values()]))

        # Deduplicate arch-dependent properties
        for arch in self.get_archs().values():
            getattr(arch, key).difference_update(getattr(self, key))

    def _finalize_non_set_attribute(self, key):
        # Only when all the arch has the same non empty value, move the value to the target common
        val = getattr(list(self.get_archs().values())[0], key)
        if val and all([val == getattr(arch, key) for arch in self.get_archs().values()]):
            setattr(self, key, copy.deepcopy(val))

    def _finalize_attribute(self, key):
        val = getattr(self, key)
        if isinstance(val, set):
            self._finalize_set_attribute(key)
        elif isinstance(val, (list, str)):
            self._finalize_non_set_attribute(key)
        else:
            raise TypeError(f'Unsupported type: {type(val)}')

    def finalize(self):
        """Move common properties out of arch-dependent subobjects to Target object.

          TODO: find a better name for this function.
          """
        if self.is_finalized:
            return
        self.is_finalized = True

        if len(self.arch) == 1:
            return

        for key in ('sources', 'cflags', 'defines', 'include_dirs', 'deps', 'source_set_deps',
                    'inputs', 'outputs', 'args', 'response_file_contents', 'ldflags'):
            self._finalize_attribute(key)

    def get_target_name(self):
        return self.name[self.name.find(":") + 1:]

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

import utils


class GnTarget:
    def __init__(self, name: str):
        super().__init__()
        self.name: str = name
        self.host_supported: bool = False
        self.device_supported: bool = False
        self.is_finalized = False
        self._variant_deps: Set[GnTarget] = set()
        # This field name is intentionally not named as `variant` to make sure that it is not
        # a variant attribute.
        self._all_variants: Dict[utils.Variant, GnTarget] = {}

    def get_deps(self, variant: utils.Variant):
        return self._all_variants[variant]._variant_deps

    def device_supported(self):
        return any([name.value.startswith('android') for name in self._variant.keys()])

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
        elif isinstance(val, (list, str)):
            self._finalize_non_set_attribute(key)
        elif isinstance(val, GnTarget):
            # Explicitly Ignore GnTarget attributes are those are just Variant targets.
            pass
        else:
            raise TypeError(f'Unsupported type: {type(val)}')

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

    def create_variant(self, variant: utils.Variant) -> None:
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

        :param variant: Type of the new variant according to utils.Variant
        """
        self._all_variants[variant] = type(self)(self.name)

    def get_variants(self) -> Dict[utils.Variant, GnTarget]:
        """ Returns a dict of variants """
        return {variant: val for variant, val in self._all_variants.items()}


class GNAction(GnTarget):
    def __init__(self, name: str):
        super().__init__(name)
        self.script: str = ""
        self._variant_inputs: Set[str] = set()
        self._variant_outputs: Set[str] = set()
        self._variant_args: List[str] = []
        self._variant_response_file_contents: str = ""

    def get_inputs(self, variant: utils.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_inputs

    def set_inputs(self, variant: utils.Variant, inputs: Set[str]):
        self._all_variants[variant]._variant_inputs = inputs

    def get_outputs(self, variant: utils.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_outputs

    def set_outputs(self, variant: utils.Variant, outputs: Set[str]):
        self._all_variants[variant]._variant_outputs = outputs

    def get_args(self, variant: utils.Variant) -> List[str]:
        return self._all_variants[variant]._variant_args

    def set_args(self, variant: utils.Variant, args: List[str]):
        self._all_variants[variant]._variant_args = args

    def get_response_file_contents(self, variant: utils.Variant) -> str:
        return self._all_variants[variant]._variant_response_file_contents

    def set_response_file_contents(self, variant: utils.Variant, value: str):
        self._all_variants[variant]._variant_response_file_contents = value


class _SourcesGnTarget(GnTarget):
    def __init__(self, name: str):
        super().__init__(name)
        self._variant_sources: Set[str] = set()

    def get_sources(self, variant: utils.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_sources

    def set_sources(self, variant: utils.Variant, sources: Set[str]):
        self._all_variants[variant]._variant_sources = sources


class ProtoGnTarget(GnTarget, _SourcesGnTarget):
    def __init__(self, name: str):
        super().__init__(name)
        self.proto_plugin: str = ""
        self.proto_in_dir: str = ""


class SourceSetGnTarget(ProtoGnTarget, GnTarget):
    def __init__(self, name: str):
        super().__init__(name)
        self.proto_deps: Set[str] = set()
        self.transitive_proto_deps = set()


class CppGnTarget(GnTarget, _SourcesGnTarget):
    """
    Represents a CPP GN Target. This class defines the blueprint for
    """

    def __init__(self, name: str):
        super().__init__(name)
        self.output_name: str = ""
        self._variant_cflags: Set[str] = set()
        self._variant_defines: Set[str] = set()
        self._variant_include_dirs: Set[str] = set()
        self._variant_transitive_static_libs_deps: Set[str] = set()
        self._variant_source_set_deps: Set[str] = set()
        self._variant_ldflags: Set[str] = set()
        self._variant_libs: Set[str] = set()
        self._variant_rtti: bool = False

    def get_cflags(self, variant: utils.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_cflags

    def set_cflags(self, variant: utils.Variant, cflags: Set[str]):
        self._all_variants[variant]._variant_cflags = cflags

    def get_defines(self, variant: utils.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_defines

    def set_defines(self, variant: utils.Variant, defines: Set[str]):
        self._all_variants[variant]._variant_defines = defines

    def get_include_dirs(self, variant: utils.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_include_dirs

    def set_include_dirs(self, variant: utils.Variant, include_dirs: Set[str]):
        self._all_variants[variant]._variant_include_dirs = include_dirs

    def get_ldflags(self, variant: utils.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_ldflags

    def get_source_set_deps(self, variant: utils.Variant) -> Set[str]:
        return self._all_variants[variant]._variant_source_set_deps

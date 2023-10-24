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
from typing import Dict, Set, Callable, List

import gn_target, utils, constants


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
        self.builtin_deps: Dict[str, Callable] = builtin_deps
        self.all_targets: Dict[str, gn_target.GnTarget] = {}
        self.java_sources: Dict[str, Set[str]] = collections.defaultdict(set)
        self.aidl_local_include_dirs: Set[str] = set()
        self.java_actions: Dict[str, Set[str]] = collections.defaultdict(set)

    def get_target(self, gn_target_name: str) -> gn_target.GnTarget | None:
        """
        Returns a GnTarget object from the fully qualified GN target name.

        :param gn_target_name: GN target label
        :return: GnTarget if there exists a target with such name.
        """
        if gn_target_name in self.all_targets:
            self.all_targets[gn_target_name].finalize()
            return self.all_targets[gn_target_name]
        return None

    def _is_target_and_variant_visited(self, gn_target_name: str,
                                       variant: constants.Variant) -> bool:
        return gn_target_name in self.all_targets and \
            variant in self.all_targets[gn_target_name].get_variants()

    def _is_target_visited(self, gn_target_name: str) -> bool:
        return gn_target_name in self.all_targets

    def parse_gn_desc(self, gn_desc: Dict[str, Dict[str, str | List[str]]], gn_target_name: str,
                      java_group_name: str | None = None,
                      is_test_target: bool = False) -> gn_target.GnTarget | None:
        """Parses a gn desc tree and resolves all target dependencies.

        It bubbles up variables from source_set dependencies as described in the
        class-level comments.
        """
        target_name = utils.label_without_toolchain(gn_target_name)
        desc = gn_desc[gn_target_name]
        type_ = desc['type']
        variant = utils.get_variant(desc['toolchain'])

        if target_name in self.builtin_deps:
            # return early, no need to parse any further as the module is a builtin.
            return gn_target.BuiltInGnTarget(target_name)

        if utils.is_java_group(type_, target_name):
            java_group_name = target_name

        target = None
        if self._is_target_and_variant_visited(target_name, variant):
            return self.all_targets[target_name]
        elif self._is_target_visited(target_name):
            target = self.all_targets[target_name]
        else:
            target = utils.create_appropriate_gn_target(target_name, desc)
            self.all_targets[target_name] = target

        target.populate_variant(desc)

        # Recurse in dependencies.
        for gn_dep_name in desc.get('deps', []):
            dep_target = self.parse_gn_desc(gn_desc, gn_dep_name, java_group_name, is_test_target)
            target.add_dependency(variant, dep_target)
        return target

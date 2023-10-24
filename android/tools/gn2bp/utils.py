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

import os
import re
from enum import Enum
from typing import List, Dict

import gn_target

LINKER_UNIT_TYPES = ('executable', 'shared_library', 'static_library', 'source_set')
JAVA_BANNED_SCRIPTS = [
    "//build/android/gyp/turbine.py",
    "//build/android/gyp/compile_java.py",
    "//build/android/gyp/filter_zip.py",
    "//build/android/gyp/dex.py",
    "//build/android/gyp/write_build_config.py",
    "//build/android/gyp/create_r_java.py",
    "//build/android/gyp/ijar.py",
    "//build/android/gyp/create_r_java.py",
    "//build/android/gyp/bytecode_processor.py",
    "//build/android/gyp/prepare_resources.py",
    "//build/android/gyp/aar.py",
    "//build/android/gyp/zip.py",
]
RESPONSE_FILE = '{{response_file_name}}'
TESTING_SUFFIX = "__testing"
AIDL_INCLUDE_DIRS_REGEX = r'--includes=\[(.*)\]'
VARIANT_PREFIX = "_variant"
CFLAG_RTTI = "-frtti"


class Variant(Enum):
    COMMON = "common"
    X86 = "android_x86"
    X86_64 = "android_x86_64"
    ARM = "android_arm"
    ARM_64 = "android_arm64"
    HOST = "host"


def get_variant(toolchain: str) -> Variant:
    if toolchain == '//build/toolchain/android:android_clang_x86':
        return Variant.X86
    elif toolchain == '//build/toolchain/android:android_clang_x64':
        return Variant.X86_64
    elif toolchain == '//build/toolchain/android:android_clang_arm':
        return Variant.ARM
    elif toolchain == '//build/toolchain/android:android_clang_arm64':
        return Variant.ARM_64
    else:
        return Variant.HOST


def is_java_group(type: str, target_name: str) -> bool:
    """
    Determines whether the provided target is a group for java targets. Java targets are compiled
    in GN by having a target of type `group` as a source depending on multiple automatically generated
    targets that does the actual compilation.

    Per https://chromium.googlesource.com/chromium/src/build/+/HEAD/android/docs/java_toolchain.md
    java target names must end in "_java".

    :param type: GN target type
    :param target_name: GN target label
    :return: True if the group is a group for java targets
    """
    return type == 'group' and target_name.endswith('_java')


def escape_response_file_contents(response_file_contents: List[str]) -> str:
    """
    Formats and escapes the response_file_contents list provided and return an escaped-string.

    Example:
    escape_response_file_contents(['--flags', '--flag=true && false']) ->
         '--flags --flag=\"true && false\"'

    :param response_file_contents: A list of strings
    :return: The escaped-string representation of the response_file_contents
    """

    formatted_flags = []
    for flag in response_file_contents:
        if '=' in flag:
            key, val = flag.split('=')
            formatted_flags.append('%s=\\"%s\\"' % (key, val))
        else:
            formatted_flags.append(flag)

    return ' '.join(formatted_flags)


def repo_root() -> str:
    """Returns an absolute path to the repository root."""
    return os.path.join(
        os.path.realpath(os.path.dirname(__file__)), os.path.pardir)


def clean_string(val: str) -> str:
    """
    Does the following operations in the same order listed:
    * Remove all escape character '\'
    * Remove all '../../', such strings occurs because the data is generated from
      chromium repository where the output directory is two-levels nested.
    * Remove all '"'

    :param val: Original String
    :return: New string after performing the operations listed above.
    """
    return val.replace('\\', '').replace('../../', '').replace('"', '').strip()


def extract_includes_from_aidl_args(args: List[str]) -> List[str]:
    """
    Extracts the aidl dependency from args provided.

    This is an example of the args could look like:
    [ "--aidl-path", "../../third_party/android_sdk/public/build-tools/33.0.0/aidl",
    "--imports=[\"../../third_party/android_sdk/public/platforms/android-33/framework.aidl\"]",
    "--srcjar", "gen/base/test/test_support_java_aidl.srcjar",
    "--depfile", "gen/base/test/test_support_java_aidl.d",
    "--includes=[\"../../base/test/android/java/src\", \"../../base/android/java/src\"]",
    "../../base/test/android/java/src/org/chromium/base/ITestCallback.aidl",
    "../../base/test/android/java/src/org/chromium/base/ITestController.aidl" ],

    :param args: An array of strings that are retrieved from an AIDL GN Action
    :return: The includes after calling clean_string on each field.
    """
    for arg in args:
        is_match = re.match(AIDL_INCLUDE_DIRS_REGEX, arg)
        if is_match:
            local_includes = is_match.group(1).split(",")
            return [clean_string(local_include) for local_include in local_includes]
    return []


def label_to_path(label: str) -> str:
    """
    Turn a GN output label (e.g., //some_dir/file.cc) into a path.

    Example:

    label_to_path("//some_dir/file.cc") -> some_dir/file.cc
    label_to_path("//") -> ./

    :param label: GN label
    :return: The path of the label, if the path is empty then returns "./"
    """
    assert label.startswith('//')
    return label[2:] or "./"


def label_without_toolchain(label: str) -> str:
    """
    Strips the toolchain from a GN label if it exists otherwise it returns the same string.

    :param label: GN label
    :return: a GN label (e.g //buildtools:protobuf(//gn/standalone/toolchain:gcc_like_host)
    without the parenthesised toolchain part.
    """
    return label.split('(')[0]


def is_java_source(src: str) -> bool:
    """
    Determine whether the provided src is pointing to a java file or not.

    :param src: Path to a src file from GN perspective
    :return: True if the file is a java source file and is not automatically generated.
    """
    return os.path.splitext(src)[1] == '.java' and not src.startswith("//out/")


def get_proto_in_dir(args: List[str]) -> str:
    """
    Extracts the value of the parameter --proto-in-dir

    Example:
    get_proto_in_dir(["--protoc", "./clang_x64/protoc",
    "--proto-in-dir", "../../components/cronet/android/proto"])
        -> "components/cronet/android/proto"

    :param args: List of strings, usually those strings are parameters to a script.
    :return: The value of the --proto-in-dir parameter.
    """
    return clean_string(args[args.index('--proto-in-dir') + 1])


def is_proto_target(gn_target_type: str, gn_target_script: str) -> bool:
    """
    Determines if the given target is a GN proto target. This is a heuristic way to figure out
    if the target provided is a proto target or not.

    :param gn_target_type: GN target type
    :param gn_target_script: The script path which will be used to execute the GN Target
    :return: True if the target matches the proto target description.
    """
    return gn_target_type == 'action' and \
        gn_target_script == "//tools/protoc_wrapper/protoc_wrapper.py"


def is_variant_attribute(key: str) -> bool:
    """
    Variant Attributes are attributes for which the finalize method of the GnTarget will try
    to move the common elements for all variants to the parent
    and deduplicate them from each variant.

    :param key: The field name that exists within __dict__
    :return: True if the key can be a variant.
    """
    return key.startswith(VARIANT_PREFIX)


def create_appropriate_gn_target(desc: Dict[str,]) -> gn_target.GnTarget:
    """
    Creates the appropriate GnTarget class for the given dictionary `desc`.

    :param desc: A dictionary that describes a Gn-target, See data/desc_*.json files for an example
    :return: A target that inherits from GnTarget or throws an exception if it can't identify
    any possible class.
    """
    type = desc['type']
    name = label_without_toolchain(desc["name"])
    if is_proto_target(type, desc.get("script", "")):
        return gn_target.ProtoGnTarget(name)
    elif type == 'source_set':
        return gn_target.SourceSetGnTarget(name)
    elif type in ["shared_library", "static_library"]:
        return gn_target.CppGnTarget(name)
    elif (desc.get("script", "") in JAVA_BANNED_SCRIPTS
          or is_java_group(type, name)):
        return gn_target.JavaGnTarget(name)
    elif type in ['action', 'action_foreach']:
        return gn_target.GnActionTarget(name)
    else:
        raise Exception(
            "%s GN target does not translate to any of the known GN classes" % name)


def remove_gen_path(path: str) -> str:
    """
    Remove the prefix for the output path

    Example:
    remove_gen_path("//out/cronet/gen/A/B/C") -> "A/B/C"

    :param path: The path to the generated output.
    :return The path of the file relative to the repository root instead of the output root
    """
    return re.sub("^//out/.+?/gen/", "", path)

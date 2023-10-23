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


def repo_root():
    """Returns an absolute path to the repository root."""
    return os.path.join(
        os.path.realpath(os.path.dirname(__file__)), os.path.pardir)


def clean_string(str):
    """
    Does the following operations in the same order listed:
    * Remove all escape character '\'
    * Remove all '../../', such strings occurs because the data is generated from
      chromium repository where the output directory is two-levels nested.
    * Remove all '"'

    :param str: Original String
    :return: New string after performing the operations listed above.
    """
    return str.replace('\\', '').replace('../../', '').replace('"', '').strip()


def extract_includes_from_aidl_args(args):
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


def label_to_path(label):
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


def label_without_toolchain(label):
    """
    Strips the toolchain from a GN label if it exists otherwise it returns the same string.

    :param label: GN label
    :return: a GN label (e.g //buildtools:protobuf(//gn/standalone/toolchain:gcc_like_host)
    without the parenthesised toolchain part.
    """
    return label.split('(')[0]


def is_java_source(src):
    """
    Determine whether the provided src is pointing to a java file or not.

    :param src: Path to a src file from GN perspective
    :return: True if the file is a java source file and is not automatically generated.
    """
    return os.path.splitext(src)[1] == '.java' and not src.startswith("//out/")

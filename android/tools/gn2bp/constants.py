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
from enum import Enum

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

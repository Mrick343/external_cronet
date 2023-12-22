//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <iostream>

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// istream wcin;

// UNSUPPORTED: no-wide-characters
// REQUIRES: target={{.+}}-windows-{{.+}}

// UNSUPPORTED: executor-has-no-bash
// FILE_DEPENDENCIES: send-stdin.sh, test.dat
// RUN: %{build}
// RUN: %{exec} bash send-stdin.sh "%t.exe" "test.dat"
=======
// wistream wcin;

// UNSUPPORTED: no-wide-characters
// REQUIRES: target={{.+}}-windows-{{.+}}

// FILE_DEPENDENCIES: test.dat
// RUN: %{build}
// RUN: cat test.dat | %{exec} %t.exe
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

// Check that wcin works, preserving the unicode characters, after switching
// stdin to wide mode.

#include <iostream>
#include <cassert>
#include <io.h>
#include <fcntl.h>

int main(int, char**) {
    _setmode(_fileno(stdin), _O_WTEXT);
    std::wstring str;
    std::wcin >> str;
    assert(str == L"1234\u20ac\u00e5\u00e4\u00f6");
    return 0;
}

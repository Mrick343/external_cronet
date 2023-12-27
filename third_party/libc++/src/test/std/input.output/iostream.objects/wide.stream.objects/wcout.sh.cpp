//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <iostream>

<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// istream wcout;

// XFAIL: no-wide-characters

// UNSUPPORTED: executor-has-no-bash
// FILE_DEPENDENCIES: ../check-stdout.sh
// RUN: %{build}
// RUN: %{exec} bash check-stdout.sh "%t.exe" "1234"
=======
// wostream wcout;

// UNSUPPORTED: no-wide-characters

// RUN: %{build}
// RUN: %{exec} %t.exe > %t.actual
// RUN: echo -n 1234 > %t.expected
// RUN: diff %t.expected %t.actual
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)

#include <iostream>

int main(int, char**) {
    std::wcout << L"1234";
    return 0;
}

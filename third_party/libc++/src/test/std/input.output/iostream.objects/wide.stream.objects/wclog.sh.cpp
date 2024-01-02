//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <iostream>

<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// istream wclog;

// XFAIL: no-wide-characters

// UNSUPPORTED: executor-has-no-bash
// FILE_DEPENDENCIES: ../check-stderr.sh
// RUN: %{build}
// RUN: %{exec} bash check-stderr.sh "%t.exe" "1234"
=======
// wostream wclog;

// XFAIL: no-wide-characters

// RUN: %{build}
// RUN: %{exec} %t.exe 2> %t.actual
// RUN: echo -n 1234 > %t.expected
// RUN: diff %t.expected %t.actual
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

#include <iostream>

int main(int, char**) {
    std::wclog << L"1234";
    return 0;
}

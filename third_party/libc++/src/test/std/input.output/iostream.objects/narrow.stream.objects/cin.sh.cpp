//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// <iostream>

// istream cin;

// UNSUPPORTED: executor-has-no-bash
// FILE_DEPENDENCIES: ../send-stdin.sh
// RUN: %{build}
// RUN: %{exec} bash send-stdin.sh "%t.exe" "1234"
=======
// TODO: Investigate
// UNSUPPORTED: LIBCXX-AIX-FIXME

// This test hangs on Android devices that lack shell_v2, which was added in
// Android N (API 24).
// UNSUPPORTED: LIBCXX-ANDROID-FIXME && android-device-api={{2[1-3]}}

// <iostream>

// istream cin;

// RUN: %{build}
// RUN: echo -n 1234 | %{exec} %t.exe
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

#include <iostream>
#include <cassert>

int main(int, char**) {
    int i;
    std::cin >> i;
    assert(i == 1234);
    return 0;
}

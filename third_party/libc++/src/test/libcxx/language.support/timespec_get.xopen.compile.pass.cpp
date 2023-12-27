//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// This test breaks when enabling LSV.
<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// UNSUPPORTED: modules-build
=======
// UNSUPPORTED: clang-modules-build
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)

// UNSUPPORTED: c++03, c++11, c++14

// Make sure that <ctime> can be included even when _XOPEN_SOURCE is defined.
// This used to trigger some bug in Apple SDKs, since timespec_get was not
// defined in <time.h> but we tried using it from <ctime>.
// See https://llvm.org/PR47208 for details.

// ADDITIONAL_COMPILE_FLAGS: -D_XOPEN_SOURCE=500

#include <ctime>

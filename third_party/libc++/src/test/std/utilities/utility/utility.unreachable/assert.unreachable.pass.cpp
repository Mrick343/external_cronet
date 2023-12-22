//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// UNSUPPORTED: c++03, c++11, c++14, c++17, c++20

// REQUIRES: has-unix-headers
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -D_LIBCPP_ENABLE_ASSERTIONS=1
=======
// REQUIRES: has-unix-headers
// UNSUPPORTED: c++03, c++11, c++14, c++17, c++20
// REQUIRES: libcpp-hardening-mode={{safe|debug}}
// XFAIL: availability-verbose_abort-missing
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

// Make sure that reaching std::unreachable() with assertions enabled triggers an assertion.

#include <utility>

#include "check_assertion.h"

int main(int, char**) {
    TEST_LIBCPP_ASSERT_FAILURE(std::unreachable(), "std::unreachable() was reached");

    return 0;
}

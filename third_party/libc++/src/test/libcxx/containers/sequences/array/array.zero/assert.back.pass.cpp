//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// REQUIRES: has-unix-headers
// UNSUPPORTED: c++03
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -D_LIBCPP_ENABLE_ASSERTIONS=1
=======
// UNSUPPORTED: libcpp-hardening-mode=unchecked
// XFAIL: availability-verbose_abort-missing
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

// test that array<T, 0>::back() triggers an assertion

#include <array>

#include "check_assertion.h"

int main(int, char**) {
  {
    typedef std::array<int, 0> C;
    C c = {};
    C const& cc = c;
    TEST_LIBCPP_ASSERT_FAILURE(c.back(), "cannot call array<T, 0>::back() on a zero-sized array");
    TEST_LIBCPP_ASSERT_FAILURE(cc.back(), "cannot call array<T, 0>::back() on a zero-sized array");
  }
  {
    typedef std::array<const int, 0> C;
    C c = {{}};
    C const& cc = c;
    TEST_LIBCPP_ASSERT_FAILURE(c.back(), "cannot call array<T, 0>::back() on a zero-sized array");
    TEST_LIBCPP_ASSERT_FAILURE(cc.back(), "cannot call array<T, 0>::back() on a zero-sized array");
  }

  return 0;
}

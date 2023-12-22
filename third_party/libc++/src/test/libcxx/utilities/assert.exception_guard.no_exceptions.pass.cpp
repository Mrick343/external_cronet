//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// UNSUPPORTED: c++03

// REQUIRES: has-unix-headers
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -fno-exceptions -D_LIBCPP_ENABLE_ASSERTIONS=1

// ADDITIONAL_COMPILE_FLAGS: -Wno-private-header
=======
// REQUIRES: has-unix-headers
// UNSUPPORTED: c++03
// REQUIRES: libcpp-hardening-mode={{safe|debug}}
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -fno-exceptions
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

#include <__utility/exception_guard.h>

#include "check_assertion.h"

int main(int, char**) {
  TEST_LIBCPP_ASSERT_FAILURE(
      std::__make_exception_guard([] {}), "__exception_guard not completed with exceptions disabled");
}

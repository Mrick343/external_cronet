//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <algorithm>

// REQUIRES: has-unix-headers
// UNSUPPORTED: c++03, c++11, c++14, c++17
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -D_LIBCPP_ENABLE_ASSERTIONS=1
=======
// REQUIRES: libcpp-hardening-mode={{safe|debug}}
// XFAIL: availability-verbose_abort-missing
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

#include <algorithm>
#include <array>

#include "check_assertion.h"

int main(int, char**) {
  std::initializer_list<int> init_list{};
  TEST_LIBCPP_ASSERT_FAILURE(std::ranges::minmax(init_list),
                             "initializer_list has to contain at least one element");

  TEST_LIBCPP_ASSERT_FAILURE(std::ranges::minmax(std::array<int, 0>{}),
                             "range has to contain at least one element");

  return 0;
}

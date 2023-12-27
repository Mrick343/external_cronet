//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// REQUIRES: has-unix-headers
<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)

// UNSUPPORTED: c++03, c++11
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -D_LIBCPP_ENABLE_ASSERTIONS=1
=======
// UNSUPPORTED: c++03, c++11
// REQUIRES: libcpp-hardening-mode={{safe|debug}}
// XFAIL: availability-verbose_abort-missing
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)

// Construct a string_view from an invalid length
// constexpr basic_string_view( const _CharT* s, size_type len )

#include <string_view>

#include "check_assertion.h"

int main(int, char**) {
  char c = 0;
  TEST_LIBCPP_ASSERT_FAILURE(
      std::string_view(&c, -1), "string_view::string_view(_CharT *, size_t): length does not fit in difference_type");
  return 0;
}

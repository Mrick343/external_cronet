//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// Call back() on empty container.

// REQUIRES: has-unix-headers
// UNSUPPORTED: c++03
<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -D_LIBCPP_ENABLE_ASSERTIONS=1

#include <string>

#include "check_assertion.h"
#include "min_allocator.h"

int main(int, char**) {
    {
        std::string s;
        TEST_LIBCPP_ASSERT_FAILURE(s.back(), "string::back(): string is empty");
    }

    {
        typedef std::basic_string<char, std::char_traits<char>, min_allocator<char> > S;
        S s;
        TEST_LIBCPP_ASSERT_FAILURE(s.back(), "string::back(): string is empty");
    }

    return 0;
=======
// UNSUPPORTED: libcpp-hardening-mode=unchecked
// XFAIL: availability-verbose_abort-missing

#include <string>

#include "check_assertion.h"
#include "min_allocator.h"

template <class S>
void test() {
  S s;
  TEST_LIBCPP_ASSERT_FAILURE(s.back(), "string::back(): string is empty");
}

int main(int, char**) {
  test<std::string>();
  test<std::basic_string<char, std::char_traits<char>, min_allocator<char> > >();

  return 0;
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)
}

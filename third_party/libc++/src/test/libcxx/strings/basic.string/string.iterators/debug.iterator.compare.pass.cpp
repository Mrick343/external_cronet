//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// Compare iterators from different containers with <.

// REQUIRES: has-unix-headers
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// UNSUPPORTED: !libcpp-has-debug-mode, c++03

#include <string>

#include "check_assertion.h"
#include "min_allocator.h"

int main(int, char**) {
  {
    typedef std::string S;
    S s1;
    S s2;
    TEST_LIBCPP_ASSERT_FAILURE(s1.begin() < s2.begin(), "Attempted to compare incomparable iterators");
  }

  {
    typedef std::basic_string<char, std::char_traits<char>, min_allocator<char> > S;
    S s1;
    S s2;
    TEST_LIBCPP_ASSERT_FAILURE(s1.begin() < s2.begin(), "Attempted to compare incomparable iterators");
  }
=======
// UNSUPPORTED: !libcpp-has-legacy-debug-mode, c++03

#include <string>

#include "check_assertion.h"
#include "min_allocator.h"

template <class S>
void test() {
  S s1;
  S s2;
  TEST_LIBCPP_ASSERT_FAILURE(s1.begin() < s2.begin(), "Attempted to compare incomparable iterators");
}

int main(int, char**) {
  test<std::string>();
  test<std::basic_string<char, std::char_traits<char>, min_allocator<char> > >();
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

  return 0;
}

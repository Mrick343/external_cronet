//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// void pop_back();

// REQUIRES: has-unix-headers
// UNSUPPORTED: c++03
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -D_LIBCPP_ENABLE_ASSERTIONS=1

#include <string>

#include "check_assertion.h"

int main(int, char**) {
    std::string s;
    TEST_LIBCPP_ASSERT_FAILURE(s.pop_back(), "string::pop_back(): string is already empty");

    return 0;
=======
// UNSUPPORTED: libcpp-hardening-mode=unchecked
// XFAIL: availability-verbose_abort-missing

#include <string>

#include "check_assertion.h"

template <class S>
void test() {
  S s;
  TEST_LIBCPP_ASSERT_FAILURE(s.pop_back(), "string::pop_back(): string is already empty");
}

int main(int, char**) {
  test<std::string>();

  return 0;
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
}

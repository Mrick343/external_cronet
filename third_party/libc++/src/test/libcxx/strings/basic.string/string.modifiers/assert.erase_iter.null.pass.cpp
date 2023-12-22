//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// Call erase(const_iterator position) with end()

// REQUIRES: has-unix-headers
// UNSUPPORTED: c++03
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -D_LIBCPP_ENABLE_ASSERTIONS=1

#include <string>

#include "check_assertion.h"
#include "min_allocator.h"

int main(int, char**) {
  {
    typedef std::basic_string<char, std::char_traits<char>, min_allocator<char> > S;
    S l1("123");
    S::const_iterator i = l1.end();
    TEST_LIBCPP_ASSERT_FAILURE(l1.erase(i), "string::erase(iterator) called with a non-dereferenceable iterator");
  }

  {
    std::string l1("123");
    std::string::const_iterator i = l1.end();
    TEST_LIBCPP_ASSERT_FAILURE(l1.erase(i), "string::erase(iterator) called with a non-dereferenceable iterator");
  }
=======
// UNSUPPORTED: libcpp-hardening-mode=unchecked
// XFAIL: availability-verbose_abort-missing

#include <string>

#include "check_assertion.h"
#include "min_allocator.h"

template <class S>
void test() {
  S l1("123");
  typename S::const_iterator i = l1.end();
  TEST_LIBCPP_ASSERT_FAILURE(l1.erase(i), "string::erase(iterator) called with a non-dereferenceable iterator");
}

int main(int, char**) {
  test<std::string>();
  test<std::basic_string<char, std::char_traits<char>, min_allocator<char> > >();
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

  return 0;
}

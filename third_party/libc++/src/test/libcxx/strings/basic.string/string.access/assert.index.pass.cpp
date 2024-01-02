//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// Index string out of bounds.

// REQUIRES: has-unix-headers
// UNSUPPORTED: c++03
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -D_LIBCPP_ENABLE_ASSERTIONS=1

#include <string>
#include <cassert>

#include "check_assertion.h"
#include "min_allocator.h"

int main(int, char**) {
  {
    typedef std::string S;
    S s;
    assert(s[0] == 0);
    TEST_LIBCPP_ASSERT_FAILURE(s[1], "string index out of bounds");
  }

  {
    typedef std::basic_string<char, std::char_traits<char>, min_allocator<char> > S;
    S s;
    assert(s[0] == 0);
    TEST_LIBCPP_ASSERT_FAILURE(s[1], "string index out of bounds");
  }
=======
// UNSUPPORTED: libcpp-hardening-mode=unchecked
// XFAIL: availability-verbose_abort-missing

#include <string>
#include <cassert>

#include "check_assertion.h"
#include "min_allocator.h"

template <class S>
void test() {
  S s;
  assert(s[0] == 0);
  TEST_LIBCPP_ASSERT_FAILURE(s[1], "string index out of bounds");
}

int main(int, char**) {
  test<std::string>();
  test<std::basic_string<char, std::char_traits<char>, min_allocator<char> > >();
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

  return 0;
}

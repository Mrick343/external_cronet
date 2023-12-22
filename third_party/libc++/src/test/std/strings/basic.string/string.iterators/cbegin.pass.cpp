//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// const_iterator cbegin() const; // constexpr since C++20

#include <string>
#include <cassert>

#include "test_macros.h"
#include "min_allocator.h"

template <class S>
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
TEST_CONSTEXPR_CXX20 void
test(const S& s)
{
    typename S::const_iterator cb = s.cbegin();
    if (!s.empty())
    {
        assert(*cb == s[0]);
    }
    assert(cb == s.begin());
}

TEST_CONSTEXPR_CXX20 bool test() {
  {
    typedef std::string S;
    test(S());
    test(S("123"));
  }
#if TEST_STD_VER >= 11
  {
    typedef std::basic_string<char, std::char_traits<char>, min_allocator<char>> S;
    test(S());
    test(S("123"));
  }
#endif

  return true;
}

int main(int, char**)
{
=======
TEST_CONSTEXPR_CXX20 void test(const S& s) {
  typename S::const_iterator cb = s.cbegin();
  if (!s.empty()) {
    assert(*cb == s[0]);
  }
  assert(cb == s.begin());
}

template <class S>
TEST_CONSTEXPR_CXX20 void test_string() {
  test(S());
  test(S("123"));
}

TEST_CONSTEXPR_CXX20 bool test() {
  test_string<std::string>();
#if TEST_STD_VER >= 11
  test_string<std::basic_string<char, std::char_traits<char>, min_allocator<char> > >();
#endif

  return true;
}

int main(int, char**) {
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
  test();
#if TEST_STD_VER > 17
  static_assert(test());
#endif

  return 0;
}

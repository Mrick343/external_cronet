//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// basic_string(const basic_string<charT,traits,Allocator>& str); // constexpr since C++20

#include <string>
#include <cassert>

#include "test_macros.h"
#include "test_allocator.h"
#include "min_allocator.h"

template <class S>
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
TEST_CONSTEXPR_CXX20 void
test(S s1)
{
    S s2 = s1;
    LIBCPP_ASSERT(s2.__invariants());
    assert(s2 == s1);
    assert(s2.capacity() >= s2.size());
    assert(s2.get_allocator() == s1.get_allocator());
}

TEST_CONSTEXPR_CXX20 bool test() {
  {
    typedef test_allocator<char> A;
    typedef std::basic_string<char, std::char_traits<char>, A> S;
    test(S(A(3)));
    test(S("1", A(5)));
    test(S("1234567890123456789012345678901234567890123456789012345678901234567890", A(7)));
  }
#if TEST_STD_VER >= 11
  {
    typedef min_allocator<char> A;
    typedef std::basic_string<char, std::char_traits<char>, A> S;
    test(S(A{}));
    test(S("1", A()));
    test(S("1234567890123456789012345678901234567890123456789012345678901234567890", A()));
  }
#endif

  return true;
}

int main(int, char**)
{
=======
TEST_CONSTEXPR_CXX20 void test(S s1) {
  S s2 = s1;
  LIBCPP_ASSERT(s2.__invariants());
  assert(s2 == s1);
  assert(s2.capacity() >= s2.size());
  assert(s2.get_allocator() == s1.get_allocator());
}

template <class Alloc>
TEST_CONSTEXPR_CXX20 void test_string(const Alloc& a) {
  typedef std::basic_string<char, std::char_traits<char>, Alloc> S;
  test(S(Alloc(a)));
  test(S("1", Alloc(a)));
  test(S("1234567890123456789012345678901234567890123456789012345678901234567890", Alloc(a)));
}

TEST_CONSTEXPR_CXX20 bool test() {
  test_string(std::allocator<char>());
  test_string(test_allocator<char>());
  test_string(test_allocator<char>(3));
#if TEST_STD_VER >= 11
  test_string(min_allocator<char>());
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

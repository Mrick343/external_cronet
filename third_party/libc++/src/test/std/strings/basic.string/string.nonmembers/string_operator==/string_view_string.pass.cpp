//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// we get this comparison "for free" because the string implicitly converts to the string_view

#include <string>
#include <cassert>

#include "test_macros.h"
#include "min_allocator.h"

template <class S, class SV>
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
TEST_CONSTEXPR_CXX20 void
test(SV lhs, const S& rhs, bool x)
{
    assert((lhs == rhs) == x);
}

TEST_CONSTEXPR_CXX20 bool test() {
  {
    typedef std::string S;
    typedef std::string_view SV;
    test(SV(""), S(""), true);
    test(SV(""), S("abcde"), false);
    test(SV(""), S("abcdefghij"), false);
    test(SV(""), S("abcdefghijklmnopqrst"), false);
    test(SV("abcde"), S(""), false);
    test(SV("abcde"), S("abcde"), true);
    test(SV("abcde"), S("abcdefghij"), false);
    test(SV("abcde"), S("abcdefghijklmnopqrst"), false);
    test(SV("abcdefghij"), S(""), false);
    test(SV("abcdefghij"), S("abcde"), false);
    test(SV("abcdefghij"), S("abcdefghij"), true);
    test(SV("abcdefghij"), S("abcdefghijklmnopqrst"), false);
    test(SV("abcdefghijklmnopqrst"), S(""), false);
    test(SV("abcdefghijklmnopqrst"), S("abcde"), false);
    test(SV("abcdefghijklmnopqrst"), S("abcdefghij"), false);
    test(SV("abcdefghijklmnopqrst"), S("abcdefghijklmnopqrst"), true);
  }
#if TEST_STD_VER >= 11
  {
    typedef std::basic_string<char, std::char_traits<char>, min_allocator<char>> S;
    typedef std::basic_string_view<char, std::char_traits<char>> SV;
    test(SV(""), S(""), true);
    test(SV(""), S("abcde"), false);
    test(SV(""), S("abcdefghij"), false);
    test(SV(""), S("abcdefghijklmnopqrst"), false);
    test(SV("abcde"), S(""), false);
    test(SV("abcde"), S("abcde"), true);
    test(SV("abcde"), S("abcdefghij"), false);
    test(SV("abcde"), S("abcdefghijklmnopqrst"), false);
    test(SV("abcdefghij"), S(""), false);
    test(SV("abcdefghij"), S("abcde"), false);
    test(SV("abcdefghij"), S("abcdefghij"), true);
    test(SV("abcdefghij"), S("abcdefghijklmnopqrst"), false);
    test(SV("abcdefghijklmnopqrst"), S(""), false);
    test(SV("abcdefghijklmnopqrst"), S("abcde"), false);
    test(SV("abcdefghijklmnopqrst"), S("abcdefghij"), false);
    test(SV("abcdefghijklmnopqrst"), S("abcdefghijklmnopqrst"), true);
  }
#endif

  return true;
}

int main(int, char**)
{
=======
TEST_CONSTEXPR_CXX20 void test(SV lhs, const S& rhs, bool x) {
  assert((lhs == rhs) == x);
}

template <class CharT, template <class> class Alloc>
TEST_CONSTEXPR_CXX20 void test_string() {
  using S  = std::basic_string<CharT, std::char_traits<CharT>, Alloc<CharT> >;
  using SV = std::basic_string_view<CharT, std::char_traits<CharT> >;

  test(SV(""), S(""), true);
  test(SV(""), S("abcde"), false);
  test(SV(""), S("abcdefghij"), false);
  test(SV(""), S("abcdefghijklmnopqrst"), false);
  test(SV("abcde"), S(""), false);
  test(SV("abcde"), S("abcde"), true);
  test(SV("abcde"), S("abcdefghij"), false);
  test(SV("abcde"), S("abcdefghijklmnopqrst"), false);
  test(SV("abcdefghij"), S(""), false);
  test(SV("abcdefghij"), S("abcde"), false);
  test(SV("abcdefghij"), S("abcdefghij"), true);
  test(SV("abcdefghij"), S("abcdefghijklmnopqrst"), false);
  test(SV("abcdefghijklmnopqrst"), S(""), false);
  test(SV("abcdefghijklmnopqrst"), S("abcde"), false);
  test(SV("abcdefghijklmnopqrst"), S("abcdefghij"), false);
  test(SV("abcdefghijklmnopqrst"), S("abcdefghijklmnopqrst"), true);
}

TEST_CONSTEXPR_CXX20 bool test() {
  test_string<char, std::allocator>();
#if TEST_STD_VER >= 11
  test_string<char, min_allocator>();
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

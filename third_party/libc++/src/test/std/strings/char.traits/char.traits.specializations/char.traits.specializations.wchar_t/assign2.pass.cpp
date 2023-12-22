//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<> struct char_traits<wchar_t>

// static void assign(char_type& c1, const char_type& c2);
// constexpr in C++17

// UNSUPPORTED: no-wide-characters

#include <string>
#include <cassert>

#include "test_macros.h"

#if TEST_STD_VER > 14
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
constexpr bool test_constexpr()
{
    wchar_t c = L'1';
    std::char_traits<wchar_t>::assign(c, L'a');
    return c == L'a';
}
#endif

int main(int, char**)
{
    wchar_t c = L'\0';
    std::char_traits<wchar_t>::assign(c, L'a');
    assert(c == L'a');

#if TEST_STD_VER > 14
    static_assert(test_constexpr(), "" );
=======
constexpr bool test_constexpr() {
  wchar_t c = L'1';
  std::char_traits<wchar_t>::assign(c, L'a');
  return c == L'a';
}
#endif

int main(int, char**) {
  wchar_t c = L'\0';
  std::char_traits<wchar_t>::assign(c, L'a');
  assert(c == L'a');

#if TEST_STD_VER > 14
  static_assert(test_constexpr(), "");
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
#endif

  return 0;
}

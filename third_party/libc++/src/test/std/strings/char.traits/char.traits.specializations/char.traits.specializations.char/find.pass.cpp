//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<> struct char_traits<char>

// static const char_type* find(const char_type* s, size_t n, const char_type& a);
// constexpr in C++17

#include <string>
#include <cassert>

#include "test_macros.h"

#if TEST_STD_VER > 14
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
constexpr bool test_constexpr()
{
    constexpr const char *p = "123";
    return std::char_traits<char>::find(p, 3, '1') == p
        && std::char_traits<char>::find(p, 3, '2') == p + 1
        && std::char_traits<char>::find(p, 3, '3') == p + 2
        && std::char_traits<char>::find(p, 3, '4') == nullptr;
}
#endif

int main(int, char**)
{
    char s1[] = {1, 2, 3};
    assert(std::char_traits<char>::find(s1, 3, char(1)) == s1);
    assert(std::char_traits<char>::find(s1, 3, char(2)) == s1+1);
    assert(std::char_traits<char>::find(s1, 3, char(3)) == s1+2);
    assert(std::char_traits<char>::find(s1, 3, char(4)) == 0);
    assert(std::char_traits<char>::find(s1, 3, char(0)) == 0);
    assert(std::char_traits<char>::find(NULL, 0, char(0)) == 0);

#if TEST_STD_VER > 14
    static_assert(test_constexpr(), "" );
=======
constexpr bool test_constexpr() {
  constexpr const char* p = "123";
  return std::char_traits<char>::find(p, 3, '1') == p && std::char_traits<char>::find(p, 3, '2') == p + 1 &&
         std::char_traits<char>::find(p, 3, '3') == p + 2 && std::char_traits<char>::find(p, 3, '4') == nullptr;
}
#endif

int main(int, char**) {
  char s1[] = {1, 2, 3};
  assert(std::char_traits<char>::find(s1, 3, char(1)) == s1);
  assert(std::char_traits<char>::find(s1, 3, char(2)) == s1 + 1);
  assert(std::char_traits<char>::find(s1, 3, char(3)) == s1 + 2);
  assert(std::char_traits<char>::find(s1, 3, char(4)) == 0);
  assert(std::char_traits<char>::find(s1, 3, char(0)) == 0);
  assert(std::char_traits<char>::find(NULL, 0, char(0)) == 0);

#if TEST_STD_VER > 14
  static_assert(test_constexpr(), "");
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
#endif

  return 0;
}

//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<> struct char_traits<wchar_t>

// static char_type* move(char_type* s1, const char_type* s2, size_t n);

// UNSUPPORTED: no-wide-characters

#include <string>
#include <cassert>

#include "test_macros.h"

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
TEST_CONSTEXPR_CXX20 bool test()
{
    wchar_t s1[] = {1, 2, 3};
    assert(std::char_traits<wchar_t>::move(s1, s1+1, 2) == s1);
    assert(s1[0] == wchar_t(2));
    assert(s1[1] == wchar_t(3));
    assert(s1[2] == wchar_t(3));
    s1[2] = wchar_t(0);
    assert(std::char_traits<wchar_t>::move(s1+1, s1, 2) == s1+1);
    assert(s1[0] == wchar_t(2));
    assert(s1[1] == wchar_t(2));
    assert(s1[2] == wchar_t(3));
    assert(std::char_traits<wchar_t>::move(NULL, s1, 0) == NULL);
    assert(std::char_traits<wchar_t>::move(s1, NULL, 0) == s1);

  return true;
}

int main(int, char**)
{
=======
TEST_CONSTEXPR_CXX20 bool test() {
  wchar_t s1[] = {1, 2, 3};
  assert(std::char_traits<wchar_t>::move(s1, s1 + 1, 2) == s1);
  assert(s1[0] == wchar_t(2));
  assert(s1[1] == wchar_t(3));
  assert(s1[2] == wchar_t(3));
  s1[2] = wchar_t(0);
  assert(std::char_traits<wchar_t>::move(s1 + 1, s1, 2) == s1 + 1);
  assert(s1[0] == wchar_t(2));
  assert(s1[1] == wchar_t(2));
  assert(s1[2] == wchar_t(3));
  assert(std::char_traits<wchar_t>::move(NULL, s1, 0) == NULL);
  assert(std::char_traits<wchar_t>::move(s1, NULL, 0) == s1);

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

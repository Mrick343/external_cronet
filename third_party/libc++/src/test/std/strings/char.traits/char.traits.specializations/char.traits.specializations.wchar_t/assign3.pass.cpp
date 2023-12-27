//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<> struct char_traits<wchar_t>

// static char_type* assign(char_type* s, size_t n, char_type a);

// UNSUPPORTED: no-wide-characters

#include <string>
#include <cassert>

#include "test_macros.h"

<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
TEST_CONSTEXPR_CXX20 bool test()
{
    wchar_t s2[3] = {0};
    assert(std::char_traits<wchar_t>::assign(s2, 3, wchar_t(5)) == s2);
    assert(s2[0] == wchar_t(5));
    assert(s2[1] == wchar_t(5));
    assert(s2[2] == wchar_t(5));
    assert(std::char_traits<wchar_t>::assign(NULL, 0, wchar_t(5)) == NULL);

  return true;
}

int main(int, char**)
{
  test();

#if TEST_STD_VER > 17
    static_assert(test());
=======
TEST_CONSTEXPR_CXX20 bool test() {
  wchar_t s2[3] = {0};
  assert(std::char_traits<wchar_t>::assign(s2, 3, wchar_t(5)) == s2);
  assert(s2[0] == wchar_t(5));
  assert(s2[1] == wchar_t(5));
  assert(s2[2] == wchar_t(5));
  assert(std::char_traits<wchar_t>::assign(NULL, 0, wchar_t(5)) == NULL);

  return true;
}

int main(int, char**) {
  test();

#if TEST_STD_VER > 17
  static_assert(test());
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)
#endif

  return 0;
}

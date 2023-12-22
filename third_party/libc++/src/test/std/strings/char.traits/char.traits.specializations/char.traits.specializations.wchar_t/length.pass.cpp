//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<> struct char_traits<wchar_t>

// static size_t length(const char_type* s);
// constexpr in C++17

// UNSUPPORTED: no-wide-characters

#include <string>
#include <cassert>

#include "test_macros.h"

#if TEST_STD_VER > 14
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
constexpr bool test_constexpr()
{
    return std::char_traits<wchar_t>::length(L"") == 0
        && std::char_traits<wchar_t>::length(L"abcd") == 4;
}
#endif

int main(int, char**)
{
    assert(std::char_traits<wchar_t>::length(L"") == 0);
    assert(std::char_traits<wchar_t>::length(L"a") == 1);
    assert(std::char_traits<wchar_t>::length(L"aa") == 2);
    assert(std::char_traits<wchar_t>::length(L"aaa") == 3);
    assert(std::char_traits<wchar_t>::length(L"aaaa") == 4);

#if TEST_STD_VER > 14
    static_assert(test_constexpr(), "" );
=======
constexpr bool test_constexpr() {
  return std::char_traits<wchar_t>::length(L"") == 0 && std::char_traits<wchar_t>::length(L"abcd") == 4;
}
#endif

int main(int, char**) {
  assert(std::char_traits<wchar_t>::length(L"") == 0);
  assert(std::char_traits<wchar_t>::length(L"a") == 1);
  assert(std::char_traits<wchar_t>::length(L"aa") == 2);
  assert(std::char_traits<wchar_t>::length(L"aaa") == 3);
  assert(std::char_traits<wchar_t>::length(L"aaaa") == 4);

#if TEST_STD_VER > 14
  static_assert(test_constexpr(), "");
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
#endif

  return 0;
}

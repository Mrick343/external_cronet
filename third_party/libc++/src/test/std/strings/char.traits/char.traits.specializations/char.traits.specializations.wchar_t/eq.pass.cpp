//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<> struct char_traits<wchar_t>

// static constexpr bool eq(char_type c1, char_type c2);

// UNSUPPORTED: no-wide-characters

#include <string>
#include <cassert>

#include "test_macros.h"

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
int main(int, char**)
{
    assert(std::char_traits<wchar_t>::eq(L'a', L'a'));
    assert(!std::char_traits<wchar_t>::eq(L'a', L'A'));
=======
int main(int, char**) {
  assert(std::char_traits<wchar_t>::eq(L'a', L'a'));
  assert(!std::char_traits<wchar_t>::eq(L'a', L'A'));
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

  return 0;
}

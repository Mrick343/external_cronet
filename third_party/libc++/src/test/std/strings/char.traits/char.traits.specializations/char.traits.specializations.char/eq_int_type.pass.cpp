//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<> struct char_traits<char>

// static constexpr bool eq_int_type(int_type c1, int_type c2);

#include <string>
#include <cassert>

#include "test_macros.h"

<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
int main(int, char**)
{
    assert( std::char_traits<char>::eq_int_type('a', 'a'));
    assert(!std::char_traits<char>::eq_int_type('a', 'A'));
    assert(!std::char_traits<char>::eq_int_type(std::char_traits<char>::eof(), 'A'));
    assert( std::char_traits<char>::eq_int_type(std::char_traits<char>::eof(),
                                                std::char_traits<char>::eof()));
=======
int main(int, char**) {
  assert(std::char_traits<char>::eq_int_type('a', 'a'));
  assert(!std::char_traits<char>::eq_int_type('a', 'A'));
  assert(!std::char_traits<char>::eq_int_type(std::char_traits<char>::eof(), 'A'));
  assert(std::char_traits<char>::eq_int_type(std::char_traits<char>::eof(), std::char_traits<char>::eof()));
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)

  return 0;
}

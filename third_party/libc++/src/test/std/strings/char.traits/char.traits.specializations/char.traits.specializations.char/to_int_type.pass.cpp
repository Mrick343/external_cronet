//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<> struct char_traits<char>

// static constexpr int_type to_int_type(char_type c);

#include <string>
#include <cassert>

#include "test_macros.h"

<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
int main(int, char**)
{
    assert(std::char_traits<char>::to_int_type('a') == 'a');
    assert(std::char_traits<char>::to_int_type('A') == 'A');
    assert(std::char_traits<char>::to_int_type(0) == 0);
=======
int main(int, char**) {
  assert(std::char_traits<char>::to_int_type('a') == 'a');
  assert(std::char_traits<char>::to_int_type('A') == 'A');
  assert(std::char_traits<char>::to_int_type(0) == 0);
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)

  return 0;
}

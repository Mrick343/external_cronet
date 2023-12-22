//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<> struct char_traits<char>

// static constexpr int_type not_eof(int_type c);

#include <string>
#include <cassert>

#include "test_macros.h"

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
int main(int, char**)
{
    assert(std::char_traits<char>::not_eof('a') == 'a');
    assert(std::char_traits<char>::not_eof('A') == 'A');
    assert(std::char_traits<char>::not_eof(0) == 0);
    assert(std::char_traits<char>::not_eof(std::char_traits<char>::eof()) !=
           std::char_traits<char>::eof());
=======
int main(int, char**) {
  assert(std::char_traits<char>::not_eof('a') == 'a');
  assert(std::char_traits<char>::not_eof('A') == 'A');
  assert(std::char_traits<char>::not_eof(0) == 0);
  assert(std::char_traits<char>::not_eof(std::char_traits<char>::eof()) != std::char_traits<char>::eof());
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

  return 0;
}

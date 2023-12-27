//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<> struct char_traits<char32_t>

// static constexpr bool eq(char_type c1, char_type c2);

// UNSUPPORTED: c++03

#include <string>
#include <cassert>

<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
int main(int, char**)
{
    assert(std::char_traits<char32_t>::eq(U'a', U'a'));
    assert(!std::char_traits<char32_t>::eq(U'a', U'A'));
    return 0;
=======
int main(int, char**) {
  assert(std::char_traits<char32_t>::eq(U'a', U'a'));
  assert(!std::char_traits<char32_t>::eq(U'a', U'A'));
  return 0;
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)
}

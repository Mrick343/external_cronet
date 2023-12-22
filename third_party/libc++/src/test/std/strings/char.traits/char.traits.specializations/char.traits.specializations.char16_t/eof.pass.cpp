//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<> struct char_traits<char16_t>

// static constexpr int_type eof();

#include <string>

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
int main(int, char**)
{
    std::char_traits<char16_t>::int_type i = std::char_traits<char16_t>::eof();
    ((void)i); // Prevent unused warning
    return 0;
=======
int main(int, char**) {
  std::char_traits<char16_t>::int_type i = std::char_traits<char16_t>::eof();
  ((void)i); // Prevent unused warning
  return 0;
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
}

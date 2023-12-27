//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: !stdlib=libc++ && (c++03 || c++11 || c++14)

// <string_view>
//   The string_views's value type must be the same as the traits's char_type

#include <string_view>

<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
int main(int, char**)
{
    std::basic_string_view<char, std::char_traits<wchar_t>> s;
=======
int main(int, char**) {
  std::basic_string_view<char, std::char_traits<wchar_t>> s;
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)

  return 0;
}

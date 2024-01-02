//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<> struct char_traits<char>

// static constexpr int_type eof();

#include <string>
#include <cassert>

#include "test_macros.h"

<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
int main(int, char**)
{
    assert(std::char_traits<char>::eof() == EOF);
=======
int main(int, char**) {
  assert(std::char_traits<char>::eof() == EOF);
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

  return 0;
}

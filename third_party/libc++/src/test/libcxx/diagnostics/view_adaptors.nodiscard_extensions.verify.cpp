//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// Check that view adaptors are marked [[nodiscard]] as a conforming extension

<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// UNSUPPORTED: c++03, c++11, c++14 ,c++17, c++20
=======
// UNSUPPORTED: c++03, c++11, c++14, c++17, c++20
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)

#include <ranges>
#include <vector>

void func() {
  std::vector<int> range;

  auto rvalue_view = std::views::as_rvalue(range);
  std::views::as_rvalue(range);         // expected-warning {{ignoring return value of function declared with 'nodiscard' attribute}}
  std::views::as_rvalue(rvalue_view);   // expected-warning {{ignoring return value of function declared with 'nodiscard' attribute}}
}

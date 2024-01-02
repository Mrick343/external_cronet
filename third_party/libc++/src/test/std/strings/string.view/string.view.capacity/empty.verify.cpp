//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string_view>

// class string_view

// bool empty() const noexcept;

// UNSUPPORTED: c++03, c++11, c++14, c++17

#include <string_view>

void f() {
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
    std::string_view c;
    c.empty();  // expected-warning {{ignoring return value of function declared with 'nodiscard' attribute}}
=======
  std::string_view c;
  c.empty(); // expected-warning {{ignoring return value of function declared with 'nodiscard' attribute}}
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)
}

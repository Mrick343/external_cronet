//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//
//
// UNSUPPORTED: no-threads
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
=======
// UNSUPPORTED: libcpp-has-no-experimental-stop_token
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
// UNSUPPORTED: c++03, c++11, c++14, c++17
// XFAIL: availability-synchronization_library-missing

//  [[nodiscard]] bool stop_requested() const noexcept;
//  [[nodiscard]] bool stop_possible() const noexcept;
//  [[nodiscard]] friend bool operator==(const stop_token& lhs, const stop_token& rhs) noexcept;

#include <stop_token>

void test() {
  std::stop_token st;
  st.stop_requested(); // expected-warning {{ignoring return value of function}}
  st.stop_possible();  // expected-warning {{ignoring return value of function}}
  operator==(st, st);  // expected-warning {{ignoring return value of function}}
}

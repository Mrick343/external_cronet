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

//   template<class Callback>
//   stop_callback(stop_token, Callback) -> stop_callback<Callback>;

#include <stop_token>
#include <type_traits>
#include <utility>

void test() {
  std::stop_token st;
  auto a = [] {};
  static_assert(std::is_same_v<decltype(std::stop_callback(st, a)), std::stop_callback<decltype(a)>>);
  static_assert(std::is_same_v<decltype(std::stop_callback(st, std::as_const(a))), std::stop_callback<decltype(a)>>);
  static_assert(std::is_same_v<decltype(std::stop_callback(st, std::move(a))), std::stop_callback<decltype(a)>>);
  static_assert(
      std::is_same_v<decltype(std::stop_callback(st, std::move(std::as_const(a)))), std::stop_callback<decltype(a)>>);
}

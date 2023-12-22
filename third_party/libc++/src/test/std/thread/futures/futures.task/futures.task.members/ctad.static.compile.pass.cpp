//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: no-threads
// UNSUPPORTED: c++03, c++11, c++14, c++17, c++20
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// XFAIL: clang-14, clang-15, gcc-12, apple-clang-14
=======
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

// checks that CTAD for std::packaged_task works properly with static operator() overloads

#include <future>
#include <type_traits>

struct Except {
  static int operator()(int*, long*) { return 0; }
};
static_assert(std::is_same_v<decltype(std::packaged_task{Except{}}), std::packaged_task<int(int*, long*)>>);

struct Noexcept {
  static int operator()(int*, long*) noexcept { return 0; }
};
static_assert(std::is_same_v<decltype(std::packaged_task{Noexcept{}}), std::packaged_task<int(int*, long*)>>);

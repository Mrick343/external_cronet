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

//  stop_callback(const stop_callback&) = delete;
//  stop_callback(stop_callback&&) = delete;
//  stop_callback& operator=(const stop_callback&) = delete;
//  stop_callback& operator=(stop_callback&&) = delete;

#include <stop_token>
#include <type_traits>

struct Callback {
  void operator()() const;
};

static_assert(!std::is_copy_constructible_v<std::stop_callback<Callback>>);
static_assert(!std::is_move_constructible_v<std::stop_callback<Callback>>);
static_assert(!std::is_copy_assignable_v<std::stop_callback<Callback>>);
static_assert(!std::is_move_assignable_v<std::stop_callback<Callback>>);

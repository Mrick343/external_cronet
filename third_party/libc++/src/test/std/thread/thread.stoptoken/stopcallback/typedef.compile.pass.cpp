//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//
//
// UNSUPPORTED: no-threads
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
=======
// UNSUPPORTED: libcpp-has-no-experimental-stop_token
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)
// UNSUPPORTED: c++03, c++11, c++14, c++17

// using callback_type = _Callback;

#include <stop_token>
#include <type_traits>

struct Callback {
  void operator()() const;
};

static_assert(std::is_same_v<std::stop_callback<Callback>::callback_type, Callback>);
static_assert(std::is_same_v<std::stop_callback<const Callback>::callback_type, const Callback>);
static_assert(std::is_same_v<std::stop_callback<Callback&>::callback_type, Callback&>);
static_assert(std::is_same_v<std::stop_callback<const Callback&>::callback_type, const Callback&>);
static_assert(std::is_same_v<std::stop_callback<Callback&&>::callback_type, Callback&&>);
static_assert(std::is_same_v<std::stop_callback<const Callback&&>::callback_type, const Callback&&>);

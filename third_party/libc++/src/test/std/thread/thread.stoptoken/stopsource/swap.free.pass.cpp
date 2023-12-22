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

// void swap(stop_source& rhs) noexcept;

#include <cassert>
#include <concepts>
#include <stop_token>
#include <type_traits>

#include "test_macros.h"

template <class T>
concept IsNoThrowFreeSwappable = requires(T& t) {
  { swap(t, t) } noexcept;
};

static_assert(IsNoThrowFreeSwappable<std::stop_source>);

int main(int, char**) {
  {
    std::stop_source ss1;
    std::stop_source ss2;

    assert(ss1 != ss2);

    ss2.request_stop();

    assert(!ss1.stop_requested());
    assert(ss2.stop_requested());

    swap(ss1, ss2);

    assert(ss1 != ss2);
    assert(ss1.stop_requested());
    assert(!ss2.stop_requested());
  }

  return 0;
}

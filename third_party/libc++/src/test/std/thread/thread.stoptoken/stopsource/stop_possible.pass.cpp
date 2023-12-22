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

// [[nodiscard]] bool stop_possible() const noexcept;
// Returns: true if *this has ownership of a stop state; otherwise, false.

#include <cassert>
#include <stop_token>
#include <type_traits>

#include "test_macros.h"

template <class T>
concept IsStopPossibleNoexcept = requires(const T& t) {
  { t.stop_possible() } noexcept;
};

static_assert(IsStopPossibleNoexcept<std::stop_source>);

int main(int, char**) {
  // no state
  {
    const std::stop_source st{std::nostopstate};
    assert(!st.stop_possible());
  }

  // with state
  {
    const std::stop_source st;
    assert(st.stop_possible());
  }

  return 0;
}

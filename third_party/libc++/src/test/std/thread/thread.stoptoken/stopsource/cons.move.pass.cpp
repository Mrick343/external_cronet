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

// stop_source(stop_source&&) noexcept;

#include <cassert>
#include <stop_token>
#include <type_traits>
#include <utility>

#include "test_macros.h"

static_assert(std::is_nothrow_move_constructible_v<std::stop_source>);

int main(int, char**) {
  {
    std::stop_source source;

    assert(source.stop_possible());
    assert(!source.stop_requested());

    std::stop_source source2{std::move(source)};

    assert(!source.stop_possible());
    assert(!source.stop_requested());

    assert(source2.stop_possible());
    assert(!source2.stop_requested());

    source2.request_stop();

    assert(!source.stop_possible());
    assert(!source.stop_requested());

    assert(source2.stop_possible());
    assert(source2.stop_requested());
  }

  return 0;
}

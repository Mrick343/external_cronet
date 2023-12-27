//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//
//
// UNSUPPORTED: no-threads
<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
=======
// UNSUPPORTED: libcpp-has-no-experimental-stop_token
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)
// UNSUPPORTED: c++03, c++11, c++14, c++17
// XFAIL: availability-synchronization_library-missing

// stop_token(stop_token&&) noexcept;

#include <cassert>
#include <stop_token>
#include <type_traits>
#include <utility>

#include "test_macros.h"

static_assert(std::is_nothrow_move_constructible_v<std::stop_token>);

int main(int, char**) {
  {
    std::stop_source source;
    auto st = source.get_token();

    assert(st.stop_possible());
    assert(!st.stop_requested());

    std::stop_token st2{std::move(st)};

    assert(!st.stop_possible());
    assert(!st.stop_requested());

    assert(st2.stop_possible());
    assert(!st2.stop_requested());

    source.request_stop();

    assert(!st.stop_possible());
    assert(!st.stop_requested());

    assert(st2.stop_possible());
    assert(st2.stop_requested());

  }

  return 0;
}

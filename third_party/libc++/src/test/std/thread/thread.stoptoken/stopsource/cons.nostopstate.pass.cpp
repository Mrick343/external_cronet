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

// explicit stop_source(nostopstate_t) noexcept;

#include <cassert>
#include <stop_token>
#include <type_traits>

#include "test_macros.h"

static_assert(std::is_nothrow_constructible_v<std::stop_source, std::nostopstate_t>);
// explicit
static_assert(!std::is_convertible_v<std::nostopstate_t, std::stop_source>);

int main(int, char**) {
  {
    std::stop_source ss(std::nostopstate);
    assert(!ss.stop_possible());
    assert(!ss.stop_requested());
  }

  return 0;
}

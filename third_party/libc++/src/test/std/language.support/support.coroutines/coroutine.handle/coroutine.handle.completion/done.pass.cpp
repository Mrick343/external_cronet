//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: c++03, c++11, c++14, c++17
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// UNSUPPORTED: libcpp-no-coroutines
=======
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

// <coroutine>

// template <class Promise = void>
// struct coroutine_handle;

// bool done() const

#include <coroutine>
#include <type_traits>
#include <memory>
#include <utility>
#include <cstdint>
#include <cassert>

#include "test_macros.h"

template <class Promise>
void do_test(std::coroutine_handle<Promise> const& H) {
  // FIXME Add a runtime test
  {
    ASSERT_SAME_TYPE(decltype(H.done()), bool);
    LIBCPP_ASSERT_NOT_NOEXCEPT(H.done());
  }
}

int main(int, char**)
{
  do_test(std::coroutine_handle<>{});
  do_test(std::coroutine_handle<int>{});

  return 0;
}

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

// constexpr explicit operator bool() const noexcept

#include <coroutine>
#include <type_traits>
#include <cassert>

#include "test_macros.h"

template <class C>
constexpr bool do_test() {
  static_assert(std::is_nothrow_constructible<bool, C>::value, "");
  static_assert(!std::is_convertible<C, bool>::value, "");
  {
    constexpr C c;
    static_assert(bool(c) == false, "");
  }
  { // null case
    const C c = {};
    ASSERT_NOEXCEPT(bool(c));
    assert(c.address() == nullptr);
    assert(bool(c) == false);
  }
  { // non-null case
    char dummy = 42;
    C c = C::from_address((void*)&dummy);
    assert(c.address() == &dummy);
    assert(bool(c) == true);
  }
  return true;
}

int main(int, char**)
{
  do_test<std::coroutine_handle<>>();
  do_test<std::coroutine_handle<int>>();
  static_assert(do_test<std::coroutine_handle<>>());
  static_assert(do_test<std::coroutine_handle<int>>());

  return 0;
}

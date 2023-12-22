//===----------------------------------------------------------------------===//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: c++03, c++11, c++14, c++17, c++20

// constexpr bool has_value() const noexcept;

#include <cassert>
#include <concepts>
#include <expected>
#include <type_traits>
#include <utility>

#include "test_macros.h"
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)

// Test noexcept
template <class T>
concept HasValueNoexcept =
    requires(T t) {
      { t.has_value() } noexcept;
    };

struct Foo {};
static_assert(!HasValueNoexcept<Foo>);

static_assert(HasValueNoexcept<std::expected<int, int>>);
static_assert(HasValueNoexcept<const std::expected<int, int>>);

constexpr bool test() {
  // has_value
  {
    const std::expected<void, int> e;
    assert(e.has_value());
  }

  // !has_value
  {
    const std::expected<void, int> e(std::unexpect, 5);
=======
#include "../../types.h"

// Test noexcept
template <class T>
concept HasValueNoexcept =
    requires(T t) {
      { t.has_value() } noexcept;
    };

struct Foo {};
static_assert(!HasValueNoexcept<Foo>);

static_assert(HasValueNoexcept<std::expected<int, int>>);
static_assert(HasValueNoexcept<const std::expected<int, int>>);

constexpr bool test() {
  // has_value
  {
    const std::expected<void, int> e;
    assert(e.has_value());
  }

  // !has_value
  {
    const std::expected<void, int> e(std::unexpect, 5);
    assert(!e.has_value());
  }

  // See comments of the corresponding test in
  // "expected.expected/observers/has_value.pass.cpp".
  {
    const std::expected<void, TailClobberer<1>> e(std::unexpect);
    // clang-cl does not support [[no_unique_address]] yet.
#if !(defined(TEST_COMPILER_CLANG) && defined(_MSC_VER))
    LIBCPP_STATIC_ASSERT(sizeof(TailClobberer<1>) == sizeof(e));
#endif
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
    assert(!e.has_value());
  }

  return true;
}

int main(int, char**) {
  test();
  static_assert(test());
  return 0;
}

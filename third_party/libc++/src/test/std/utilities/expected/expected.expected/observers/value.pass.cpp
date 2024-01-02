//===----------------------------------------------------------------------===//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: c++03, c++11, c++14, c++17, c++20

// constexpr const T& value() const &;
// constexpr T& value() &;
// constexpr T&& value() &&;
// constexpr const T&& value() const &&;

#include <cassert>
#include <concepts>
#include <expected>
#include <type_traits>
#include <utility>

<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
#include "MoveOnly.h"
#include "test_macros.h"

constexpr bool test() {
  // non-const &
  {
    std::expected<int, int> e(5);
    decltype(auto) x = e.value();
    static_assert(std::same_as<decltype(x), int&>);
    assert(&x == &(*e));
    assert(x == 5);
  }

  // const &
  {
    const std::expected<int, int> e(5);
    decltype(auto) x = e.value();
    static_assert(std::same_as<decltype(x), const int&>);
    assert(&x == &(*e));
    assert(x == 5);
  }

  // non-const &&
  {
    std::expected<int, int> e(5);
    decltype(auto) x = std::move(e).value();
    static_assert(std::same_as<decltype(x), int&&>);
    assert(&x == &(*e));
    assert(x == 5);
  }

  // const &&
  {
    const std::expected<int, int> e(5);
    decltype(auto) x = std::move(e).value();
    static_assert(std::same_as<decltype(x), const int&&>);
    assert(&x == &(*e));
    assert(x == 5);
  }

  return true;
}

void testException() {
#ifndef TEST_HAS_NO_EXCEPTIONS

  // int
  {
    const std::expected<int, int> e(std::unexpect, 5);
    try {
      (void) e.value();
      assert(false);
    } catch (const std::bad_expected_access<int>& ex) {
      assert(ex.error() == 5);
    }
  }

  // MoveOnly
  {
    std::expected<int, MoveOnly> e(std::unexpect, 5);
    try {
      (void) std::move(e).value();
      assert(false);
    } catch (const std::bad_expected_access<MoveOnly>& ex) {
      assert(ex.error() == 5);
    }
  }

#endif // TEST_HAS_NO_EXCEPTIONS
}

int main(int, char**) {
  test();
  static_assert(test());
  testException();
=======
#include "test_macros.h"

constexpr bool test() {
  // non-const &
  {
    std::expected<int, int> e(5);
    decltype(auto) x = e.value();
    static_assert(std::same_as<decltype(x), int&>);
    assert(&x == &(*e));
    assert(x == 5);
  }

  // const &
  {
    const std::expected<int, int> e(5);
    decltype(auto) x = e.value();
    static_assert(std::same_as<decltype(x), const int&>);
    assert(&x == &(*e));
    assert(x == 5);
  }

  // non-const &&
  {
    std::expected<int, int> e(5);
    decltype(auto) x = std::move(e).value();
    static_assert(std::same_as<decltype(x), int&&>);
    assert(&x == &(*e));
    assert(x == 5);
  }

  // const &&
  {
    const std::expected<int, int> e(5);
    decltype(auto) x = std::move(e).value();
    static_assert(std::same_as<decltype(x), const int&&>);
    assert(&x == &(*e));
    assert(x == 5);
  }

  return true;
}

void testException() {
#ifndef TEST_HAS_NO_EXCEPTIONS

  // int
  {
    const std::expected<int, int> e(std::unexpect, 5);
    try {
      (void) e.value();
      assert(false);
    } catch (const std::bad_expected_access<int>& ex) {
      assert(ex.error() == 5);
    }
  }

#endif // TEST_HAS_NO_EXCEPTIONS
}

void testAsConst() {
#ifndef TEST_HAS_NO_EXCEPTIONS
  struct Error {
    enum { Default, MutableRefCalled, ConstRefCalled } From = Default;
    Error()                                                 = default;
    Error(const Error&) { From = ConstRefCalled; }
    Error(Error&) { From = MutableRefCalled; }
    Error(Error&& e) { From = e.From; }
  };

  // Test & overload
  {
    std::expected<int, Error> e(std::unexpect, Error());
    try {
      (void)e.value();
      assert(false);
    } catch (const std::bad_expected_access<Error>& ex) {
      assert(ex.error().From == Error::ConstRefCalled);
    }
  }

  // There are no effects for `const &` overload.

#endif // TEST_HAS_NO_EXCEPTIONS
}

int main(int, char**) {
  test();
  static_assert(test());
  testException();
  testAsConst();

>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)
  return 0;
}

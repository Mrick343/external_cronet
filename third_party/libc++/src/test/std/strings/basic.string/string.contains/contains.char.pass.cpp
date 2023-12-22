//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//
// UNSUPPORTED: c++03, c++11, c++14, c++17, c++20

// <string>

// constexpr bool contains(charT x) const noexcept;

#include <string>
#include <cassert>

#include "test_macros.h"

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
constexpr bool test()
{
    using S = std::string;

    S s1 {};
    S s2 {"abcde", 5};

    ASSERT_NOEXCEPT(s1.contains('e'));

    assert(!s1.contains('c'));
    assert(!s1.contains('e'));
    assert(!s1.contains('x'));
    assert( s2.contains('c'));
    assert( s2.contains('e'));
    assert(!s2.contains('x'));

    return true;
}

int main(int, char**)
{
=======
template <class S>
constexpr void test_string() {
  S s1{};
  S s2{"abcde", 5};

  ASSERT_NOEXCEPT(s1.contains('e'));

  assert(!s1.contains('c'));
  assert(!s1.contains('e'));
  assert(!s1.contains('x'));
  assert(s2.contains('c'));
  assert(s2.contains('e'));
  assert(!s2.contains('x'));
}

constexpr bool test() {
  test_string<std::string>();

  return true;
}

int main(int, char**) {
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
  test();
  static_assert(test());

  return 0;
}

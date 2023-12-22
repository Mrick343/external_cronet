//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//
// UNSUPPORTED: c++03, c++11, c++14, c++17

// <string>

// constexpr bool starts_with(charT x) const noexcept;

#include <string>
#include <cassert>

#include "test_macros.h"

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
constexpr bool test() {
  {
    typedef std::string S;
    S  s1 {};
    S  s2 { "abcde", 5 };

    ASSERT_NOEXCEPT(s1.starts_with('e'));

    assert (!s1.starts_with('a'));
    assert (!s1.starts_with('x'));
    assert ( s2.starts_with('a'));
    assert (!s2.starts_with('x'));
  }

  return true;
}

int main(int, char**)
{
=======
template <class S>
constexpr void test_string() {
  S s1{};
  S s2{"abcde", 5};

  ASSERT_NOEXCEPT(s1.starts_with('e'));

  assert(!s1.starts_with('a'));
  assert(!s1.starts_with('x'));
  assert(s2.starts_with('a'));
  assert(!s2.starts_with('x'));
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

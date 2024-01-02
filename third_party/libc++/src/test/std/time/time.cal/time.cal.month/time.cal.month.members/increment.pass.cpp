//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//
// UNSUPPORTED: c++03, c++11, c++14, c++17

// <chrono>
// class month;

//  constexpr month& operator++() noexcept;
//  constexpr month operator++(int) noexcept;

<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)

#include <chrono>
#include <type_traits>
#include <cassert>

#include "test_macros.h"

template <typename M>
constexpr bool testConstexpr()
{
    M m1{1};
    if (static_cast<unsigned>(++m1) != 2) return false;
    if (static_cast<unsigned>(m1++) != 2) return false;
    if (static_cast<unsigned>(m1)   != 3) return false;
    return true;
}

int main(int, char**)
{
    using month = std::chrono::month;
    ASSERT_NOEXCEPT(++(std::declval<month&>())  );
    ASSERT_NOEXCEPT(  (std::declval<month&>())++);

    ASSERT_SAME_TYPE(month , decltype(  std::declval<month&>()++));
    ASSERT_SAME_TYPE(month&, decltype(++std::declval<month&>()  ));

    static_assert(testConstexpr<month>(), "");

    for (unsigned i = 0; i <= 10; ++i)
    {
        month m(i);
        assert(static_cast<unsigned>(++m) == i + 1);
        assert(static_cast<unsigned>(m++) == i + 1);
        assert(static_cast<unsigned>(m)   == i + 2);
    }
=======
#include <chrono>
#include <type_traits>
#include <cassert>

#include "test_macros.h"

constexpr bool test() {
  using month = std::chrono::month;
  for (unsigned i = 0; i <= 15; ++i) {
    month m1(i);
    month m2 = m1++;
    assert(m1.ok());
    assert(m1 != m2);

    unsigned exp = i + 1;
    while (exp > 12)
      exp -= 12;
    assert(static_cast<unsigned>(m1) == exp);
  }
  for (unsigned i = 0; i <= 15; ++i) {
    month m1(i);
    month m2 = ++m1;
    assert(m1.ok());
    assert(m2.ok());
    assert(m1 == m2);

    unsigned exp = i + 1;
    while (exp > 12)
      exp -= 12;
    assert(static_cast<unsigned>(m1) == exp);
  }

  return true;
}

int main(int, char**) {
  using month = std::chrono::month;

  ASSERT_NOEXCEPT(++(std::declval<month&>()));
  ASSERT_NOEXCEPT((std::declval<month&>())++);

  ASSERT_SAME_TYPE(month, decltype(std::declval<month&>()++));
  ASSERT_SAME_TYPE(month&, decltype(++std::declval<month&>()));

  test();
  static_assert(test());
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

  return 0;
}

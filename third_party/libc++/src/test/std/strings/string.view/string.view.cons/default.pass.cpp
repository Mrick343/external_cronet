//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: !stdlib=libc++ && (c++03 || c++11 || c++14)

// <string_view>

// constexpr basic_string_view () noexcept;

#include <string_view>
#include <cassert>

#include "test_macros.h"

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
template<typename T>
void test () {
#if TEST_STD_VER > 11
    {
    ASSERT_NOEXCEPT(T());

    constexpr T sv1;
    static_assert ( sv1.size() == 0, "" );
    static_assert ( sv1.empty(), "");
    }
#endif

    {
    T sv1;
    assert ( sv1.size() == 0 );
    assert ( sv1.empty());
    }
}

int main(int, char**) {
    test<std::string_view> ();
    test<std::u16string_view> ();
#ifndef TEST_HAS_NO_CHAR8_T
    test<std::u8string_view> ();
#endif
    test<std::u32string_view> ();
#ifndef TEST_HAS_NO_WIDE_CHARACTERS
    test<std::wstring_view> ();
=======
template <typename T>
void test() {
#if TEST_STD_VER > 11
  {
    ASSERT_NOEXCEPT(T());

    constexpr T sv1;
    static_assert(sv1.size() == 0, "");
    static_assert(sv1.empty(), "");
  }
#endif

  {
    T sv1;
    assert(sv1.size() == 0);
    assert(sv1.empty());
  }
}

int main(int, char**) {
  test<std::string_view>();
  test<std::u16string_view>();
#ifndef TEST_HAS_NO_CHAR8_T
  test<std::u8string_view>();
#endif
  test<std::u32string_view>();
#ifndef TEST_HAS_NO_WIDE_CHARACTERS
  test<std::wstring_view>();
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
#endif

  return 0;
}

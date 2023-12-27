//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: c++03, c++11, c++14, c++17

// <string_view>

//   constexpr bool ends_with(charT x) const noexcept;

#include <string_view>
#include <cassert>

#include "test_macros.h"
#include "constexpr_char_traits.h"

<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
int main(int, char**)
{
    {
    typedef std::string_view SV;
    SV  sv1 {};
    SV  sv2 { "abcde", 5 };

    ASSERT_NOEXCEPT(sv1.ends_with('e'));

    assert (!sv1.ends_with('e'));
    assert (!sv1.ends_with('x'));
    assert ( sv2.ends_with('e'));
    assert (!sv2.ends_with('x'));
    }

#if TEST_STD_VER > 11
    {
    typedef std::basic_string_view<char, constexpr_char_traits<char>> SV;
    constexpr SV  sv1 {};
    constexpr SV  sv2 { "abcde", 5 };
    static_assert (!sv1.ends_with('e'), "" );
    static_assert (!sv1.ends_with('x'), "" );
    static_assert ( sv2.ends_with('e'), "" );
    static_assert (!sv2.ends_with('x'), "" );
    }
=======
int main(int, char**) {
  {
    typedef std::string_view SV;
    SV sv1{};
    SV sv2{"abcde", 5};

    ASSERT_NOEXCEPT(sv1.ends_with('e'));

    assert(!sv1.ends_with('e'));
    assert(!sv1.ends_with('x'));
    assert(sv2.ends_with('e'));
    assert(!sv2.ends_with('x'));
  }

#if TEST_STD_VER > 11
  {
    typedef std::basic_string_view<char, constexpr_char_traits<char>> SV;
    constexpr SV sv1{};
    constexpr SV sv2{"abcde", 5};
    static_assert(!sv1.ends_with('e'), "");
    static_assert(!sv1.ends_with('x'), "");
    static_assert(sv2.ends_with('e'), "");
    static_assert(!sv2.ends_with('x'), "");
  }
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)
#endif

  return 0;
}

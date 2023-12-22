//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// template<class charT, class traits, class Allocator>
//   basic_ostream<charT, traits>&
//   operator<<(basic_ostream<charT, traits>& os,
//              const basic_string<charT,traits,Allocator>& str);

#include <string>
#include <sstream>
#include <cassert>

#include "test_macros.h"
#include "min_allocator.h"

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
int main(int, char**)
{
    {
        std::ostringstream out;
        std::string s("some text");
        out << s;
        assert(out.good());
        assert(s == out.str());
    }
    {
        std::ostringstream out;
        std::string s("some text");
        out.width(12);
        out << s;
        assert(out.good());
        assert("   " + s == out.str());
    }
#ifndef TEST_HAS_NO_WIDE_CHARACTERS
    {
        std::wostringstream out;
        std::wstring s(L"some text");
        out << s;
        assert(out.good());
        assert(s == out.str());
    }
    {
        std::wostringstream out;
        std::wstring s(L"some text");
        out.width(12);
        out << s;
        assert(out.good());
        assert(L"   " + s == out.str());
    }
#endif
#if TEST_STD_VER >= 11
    {
        typedef std::basic_string<char, std::char_traits<char>, min_allocator<char>> S;
        std::basic_ostringstream<S::value_type, S::traits_type, S::allocator_type> out;
        S s("some text");
        out << s;
        assert(out.good());
        assert(s == out.str());
    }
    {
        typedef std::basic_string<char, std::char_traits<char>, min_allocator<char>> S;
        std::basic_ostringstream<S::value_type, S::traits_type, S::allocator_type> out;
        S s("some text");
        out.width(12);
        out << s;
        assert(out.good());
        assert("   " + s == out.str());
    }
#ifndef TEST_HAS_NO_WIDE_CHARACTERS
    {
        typedef std::basic_string<wchar_t, std::char_traits<wchar_t>, min_allocator<wchar_t>> S;
        std::basic_ostringstream<S::value_type, S::traits_type, S::allocator_type> out;
        S s(L"some text");
        out << s;
        assert(out.good());
        assert(s == out.str());
    }
    {
        typedef std::basic_string<wchar_t, std::char_traits<wchar_t>, min_allocator<wchar_t>> S;
        std::basic_ostringstream<S::value_type, S::traits_type, S::allocator_type> out;
        S s(L"some text");
        out.width(12);
        out << s;
        assert(out.good());
        assert(L"   " + s == out.str());
    }
#endif // TEST_HAS_NO_WIDE_CHARACTERS
#endif // TEST_STD_VER >= 11
=======
template <template <class> class Alloc>
void test() {
  using S  = std::basic_string<char, std::char_traits<char>, Alloc<char> >;
  using OS = std::basic_ostringstream<char, std::char_traits<char>, Alloc<char> >;
  {
    OS out;
    S s("some text");
    out << s;
    assert(out.good());
    assert(s == out.str());
  }
  {
    OS out;
    S s("some text");
    out.width(12);
    out << s;
    assert(out.good());
    assert("   " + s == out.str());
  }
#ifndef TEST_HAS_NO_WIDE_CHARACTERS
  using WS  = std::basic_string<wchar_t, std::char_traits<wchar_t>, Alloc<wchar_t> >;
  using WOS = std::basic_ostringstream<wchar_t, std::char_traits<wchar_t>, Alloc<wchar_t> >;
  {
    WOS out;
    WS s(L"some text");
    out << s;
    assert(out.good());
    assert(s == out.str());
  }
  {
    WOS out;
    WS s(L"some text");
    out.width(12);
    out << s;
    assert(out.good());
    assert(L"   " + s == out.str());
  }
#endif
}

int main(int, char**) {
  test<std::allocator>();

#if TEST_STD_VER >= 11
  test<min_allocator>();
#endif
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

  return 0;
}

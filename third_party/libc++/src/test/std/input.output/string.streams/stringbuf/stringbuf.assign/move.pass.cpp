//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// <sstream>

// template <class charT, class traits = char_traits<charT>, class Allocator = allocator<charT> >
// class basic_stringbuf

// basic_stringbuf& operator=(basic_stringbuf&& rhs);

#include <sstream>
#include <cassert>

#include "test_macros.h"

int main(int, char**)
{
    {
        std::stringbuf buf1("testing");
        std::stringbuf buf;
        buf = std::move(buf1);
        assert(buf.str() == "testing");
    }
    {
        std::stringbuf buf1("testing", std::ios_base::in);
        std::stringbuf buf;
        buf = std::move(buf1);
        assert(buf.str() == "testing");
    }
    {
        std::stringbuf buf1("testing", std::ios_base::out);
        std::stringbuf buf;
        buf = std::move(buf1);
        assert(buf.str() == "testing");
    }
#ifndef TEST_HAS_NO_WIDE_CHARACTERS
    {
        std::wstringbuf buf1(L"testing");
        std::wstringbuf buf;
        buf = std::move(buf1);
        assert(buf.str() == L"testing");
    }
    {
        std::wstringbuf buf1(L"testing", std::ios_base::in);
        std::wstringbuf buf;
        buf = std::move(buf1);
        assert(buf.str() == L"testing");
    }
    {
        std::wstringbuf buf1(L"testing", std::ios_base::out);
        std::wstringbuf buf;
        buf = std::move(buf1);
        assert(buf.str() == L"testing");
    }
#endif // TEST_HAS_NO_WIDE_CHARACTERS

=======
// UNSUPPORTED: c++03

// <sstream>

// template <class charT, class traits = char_traits<charT>, class Allocator = allocator<charT> >
// class basic_stringbuf

// basic_stringbuf& operator=(basic_stringbuf&& rhs);

#include <sstream>
#include <cassert>
#include <utility>

#include "make_string.h"
#include "test_macros.h"

#define STR(S) MAKE_STRING(CharT, S)

template <class CharT>
struct test_stringbuf : std::basic_stringbuf<CharT> {
  using std::basic_stringbuf<CharT>::basic_stringbuf;

  // Checks the following requirement after being moved from:
  //    The six pointers of std::basic_streambuf in *this are guaranteed to be different
  //    from the corresponding pointers in the moved-from rhs unless null.
  void check_different_pointers(test_stringbuf<CharT> const& other) const {
    assert(this->eback() == nullptr || this->eback() != other.eback());
    assert(this->gptr() == nullptr || this->gptr() != other.gptr());
    assert(this->egptr() == nullptr || this->egptr() != other.egptr());
    assert(this->pbase() == nullptr || this->pbase() != other.pbase());
    assert(this->pptr() == nullptr || this->pptr() != other.pptr());
    assert(this->epptr() == nullptr || this->epptr() != other.epptr());
  }
};

template <class CharT>
void test() {
  std::basic_string<CharT> strings[] = {STR(""), STR("short"), STR("loooooooooooooooooooong")};
  for (std::basic_string<CharT> const& s : strings) {
    {
      test_stringbuf<CharT> buf1(s);
      test_stringbuf<CharT> buf;
      buf = std::move(buf1);
      assert(buf.str() == s);
      buf.check_different_pointers(buf1);
    }
    {
      test_stringbuf<CharT> buf1(s, std::ios_base::in);
      test_stringbuf<CharT> buf;
      buf = std::move(buf1);
      assert(buf.str() == s);
      buf.check_different_pointers(buf1);
    }
    {
      test_stringbuf<CharT> buf1(s, std::ios_base::out);
      test_stringbuf<CharT> buf;
      buf = std::move(buf1);
      assert(buf.str() == s);
      buf.check_different_pointers(buf1);
    }
    {
      test_stringbuf<CharT> buf1;
      test_stringbuf<CharT> buf;
      buf = std::move(buf1);
      buf.check_different_pointers(buf1);
    }
    // Use the assignment operator on an actual std::stringbuf, not test_stringbuf
    {
      std::basic_stringbuf<CharT> buf1(s);
      std::basic_stringbuf<CharT> buf;
      buf = std::move(buf1);
      assert(buf.str() == s);
    }
  }
}

int main(int, char**) {
  test<char>();
#ifndef TEST_HAS_NO_WIDE_CHARACTERS
  test<wchar_t>();
#endif
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)
  return 0;
}

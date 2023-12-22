//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// const charT* data() const; // constexpr since C++20
//       charT* data();   // C++17, constexpr since C++20

#include <string>
#include <cassert>

#include "test_macros.h"
#include "min_allocator.h"

template <class S>
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
TEST_CONSTEXPR_CXX20 void
test_const(const S& s)
{
    typedef typename S::traits_type T;
    const typename S::value_type* str = s.data();
    if (s.size() > 0)
    {
        assert(T::compare(str, &s[0], s.size()) == 0);
        assert(T::eq(str[s.size()], typename S::value_type()));
    }
    else
        assert(T::eq(str[0], typename S::value_type()));
}

template <class S>
TEST_CONSTEXPR_CXX20 void
test_nonconst(S& s)
{
    typedef typename S::traits_type T;
    typename S::value_type* str = s.data();
    if (s.size() > 0)
    {
        assert(T::compare(str, &s[0], s.size()) == 0);
        assert(T::eq(str[s.size()], typename S::value_type()));
    }
    else
        assert(T::eq(str[0], typename S::value_type()));
}

TEST_CONSTEXPR_CXX20 bool test() {
  {
    typedef std::string S;
    test_const(S(""));
    test_const(S("abcde"));
    test_const(S("abcdefghij"));
    test_const(S("abcdefghijklmnopqrst"));
  }
#if TEST_STD_VER >= 11
  {
    typedef std::basic_string<char, std::char_traits<char>, min_allocator<char>> S;
    test_const(S(""));
    test_const(S("abcde"));
    test_const(S("abcdefghij"));
    test_const(S("abcdefghijklmnopqrst"));
  }
#endif
#if TEST_STD_VER > 14
  {
    typedef std::string S;
    S s1("");                     test_nonconst(s1);
    S s2("abcde");                test_nonconst(s2);
    S s3("abcdefghij");           test_nonconst(s3);
    S s4("abcdefghijklmnopqrst"); test_nonconst(s4);
  }
#endif

  return true;
}

int main(int, char**)
{
=======
TEST_CONSTEXPR_CXX20 void test_const(const S& s) {
  typedef typename S::traits_type T;
  const typename S::value_type* str = s.data();
  if (s.size() > 0) {
    assert(T::compare(str, &s[0], s.size()) == 0);
    assert(T::eq(str[s.size()], typename S::value_type()));
  } else
    assert(T::eq(str[0], typename S::value_type()));
}

template <class S>
TEST_CONSTEXPR_CXX20 void test_nonconst(S& s) {
  typedef typename S::traits_type T;
  typename S::value_type* str = s.data();
  if (s.size() > 0) {
    assert(T::compare(str, &s[0], s.size()) == 0);
    assert(T::eq(str[s.size()], typename S::value_type()));
  } else
    assert(T::eq(str[0], typename S::value_type()));
}

template <class S>
TEST_CONSTEXPR_CXX20 void test_string() {
  test_const(S(""));
  test_const(S("abcde"));
  test_const(S("abcdefghij"));
  test_const(S("abcdefghijklmnopqrst"));
#if TEST_STD_VER > 14
  {
    S s1("");
    test_nonconst(s1);
    S s2("abcde");
    test_nonconst(s2);
    S s3("abcdefghij");
    test_nonconst(s3);
    S s4("abcdefghijklmnopqrst");
    test_nonconst(s4);
  }
#endif
}

TEST_CONSTEXPR_CXX20 bool test() {
  test_string<std::string>();
#if TEST_STD_VER >= 11
  test_string<std::basic_string<char, std::char_traits<char>, min_allocator<char> > >();
#endif

  return true;
}

int main(int, char**) {
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
  test();
#if TEST_STD_VER > 17
  static_assert(test());
#endif

  return 0;
}

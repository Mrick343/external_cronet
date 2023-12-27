//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// allocator_type get_allocator() const; // constexpr since C++20

#include <string>
#include <cassert>

#include "test_macros.h"
#include "test_allocator.h"
#include "min_allocator.h"

template <class S>
<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
TEST_CONSTEXPR_CXX20 void
test(const S& s, const typename S::allocator_type& a)
{
    assert(s.get_allocator() == a);
}

TEST_CONSTEXPR_CXX20 bool test() {
  {
    typedef test_allocator<char> A;
    typedef std::basic_string<char, std::char_traits<char>, A> S;
    test(S(""), A());
    test(S("abcde", A(1)), A(1));
    test(S("abcdefghij", A(2)), A(2));
    test(S("abcdefghijklmnopqrst", A(3)), A(3));
  }
#if TEST_STD_VER >= 11
  {
    typedef min_allocator<char> A;
    typedef std::basic_string<char, std::char_traits<char>, A> S;
    test(S(""), A());
    test(S("abcde", A()), A());
    test(S("abcdefghij", A()), A());
    test(S("abcdefghijklmnopqrst", A()), A());
  }
#endif

  return true;
}

int main(int, char**)
{
=======
TEST_CONSTEXPR_CXX20 void test(const S& s, const typename S::allocator_type& a) {
  assert(s.get_allocator() == a);
}

template <class Alloc>
TEST_CONSTEXPR_CXX20 void test_string(const Alloc& a) {
  using S = std::basic_string<char, std::char_traits<char>, Alloc>;
  test(S(""), Alloc());
  test(S("abcde", Alloc(a)), Alloc(a));
  test(S("abcdefghij", Alloc(a)), Alloc(a));
  test(S("abcdefghijklmnopqrst", Alloc(a)), Alloc(a));
}

TEST_CONSTEXPR_CXX20 bool test() {
  test_string(std::allocator<char>());
  test_string(test_allocator<char>());
  test_string(test_allocator<char>(1));
  test_string(test_allocator<char>(2));
  test_string(test_allocator<char>(3));
#if TEST_STD_VER >= 11
  test_string(min_allocator<char>());
#endif

  return true;
}

int main(int, char**) {
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)
  test();
#if TEST_STD_VER > 17
  static_assert(test());
#endif

  return 0;
}

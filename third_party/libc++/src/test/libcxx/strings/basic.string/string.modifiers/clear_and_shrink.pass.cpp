//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// Call __clear_and_shrink() and ensure string invariants hold

#include <string>
#include <cassert>

#include "test_macros.h"

TEST_CONSTEXPR_CXX20 bool test() {
  std::string l = "Long string so that allocation definitely, for sure, absolutely happens. Probably.";
  std::string s = "short";

  assert(l.__invariants());
  assert(s.__invariants());

  s.__clear_and_shrink();
  assert(s.__invariants());
  assert(s.size() == 0);

  std::string::size_type cap = l.capacity();
  l.__clear_and_shrink();
  assert(l.__invariants());
  assert(l.size() == 0);
  assert(l.capacity() < cap);

  return true;
}

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
int main(int, char**)
{
=======
int main(int, char**) {
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
  test();
#if TEST_STD_VER > 17
  static_assert(test());
#endif
  return 0;
}

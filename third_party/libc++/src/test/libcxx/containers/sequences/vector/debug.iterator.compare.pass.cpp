//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <vector>

// Compare iterators from different containers with <.

// REQUIRES: has-unix-headers
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// UNSUPPORTED: !libcpp-has-debug-mode, c++03
=======
// UNSUPPORTED: !libcpp-has-legacy-debug-mode, c++03
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

#include <vector>

#include "check_assertion.h"
#include "min_allocator.h"

int main(int, char**) {
  {
    typedef int T;
    typedef std::vector<T> C;
    C c1;
    C c2;
    TEST_LIBCPP_ASSERT_FAILURE(c1.begin() < c2.begin(), "Attempted to compare incomparable iterators");
  }

  {
    typedef int T;
    typedef std::vector<T, min_allocator<T> > C;
    C c1;
    C c2;
    TEST_LIBCPP_ASSERT_FAILURE(c1.begin() < c2.begin(), "Attempted to compare incomparable iterators");
  }

  return 0;
}

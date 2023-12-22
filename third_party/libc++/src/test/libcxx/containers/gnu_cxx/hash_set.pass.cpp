//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// UNSUPPORTED: modules-build
=======
// UNSUPPORTED: clang-modules-build
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

// Prevent <ext/hash_set> from generating deprecated warnings for this test.
// ADDITIONAL_COMPILE_FLAGS: -Wno-deprecated

#include <ext/hash_set>
#include <cassert>

#include "test_macros.h"
#include "count_new.h"

void test_default_does_not_allocate() {
  DisableAllocationGuard g;
  ((void)g);
  {
    __gnu_cxx::hash_set<int> h;
    assert(h.bucket_count() == 0);
  }
  {
    __gnu_cxx::hash_multiset<int> h;
    assert(h.bucket_count() == 0);
  }
}

int main(int, char**) {
  test_default_does_not_allocate();
  return 0;
}

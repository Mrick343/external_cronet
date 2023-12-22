//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <unordered_set>

// Call erase(const_iterator position) with invalid iterators

// REQUIRES: has-unix-headers
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// UNSUPPORTED: !libcpp-has-debug-mode, c++03
=======
// UNSUPPORTED: !libcpp-has-legacy-debug-mode, c++03
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

#include <unordered_set>

#include "check_assertion.h"

int main(int, char**) {
    // With end()
    {
        int a1[] = {1, 2, 3};
        std::unordered_set<int> l1(a1, a1+3);
        std::unordered_set<int>::const_iterator i = l1.end();
        TEST_LIBCPP_ASSERT_FAILURE(l1.erase(i),
                                "unordered container erase(iterator) called with a non-dereferenceable iterator");
    }

    // With iterator from another container
    {
        int a1[] = {1, 2, 3};
        std::unordered_set<int> l1(a1, a1+3);
        std::unordered_set<int> l2(a1, a1+3);
        std::unordered_set<int>::const_iterator i = l2.begin();
        TEST_LIBCPP_ASSERT_FAILURE(
            l1.erase(i), "unordered container erase(iterator) called with an iterator not referring to this container");
    }

    return 0;
}

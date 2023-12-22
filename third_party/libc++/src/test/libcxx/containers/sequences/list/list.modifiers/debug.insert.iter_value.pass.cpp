//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <list>

// iterator insert(const_iterator position, const value_type& x);

// REQUIRES: has-unix-headers
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// UNSUPPORTED: !libcpp-has-debug-mode, c++03
=======
// UNSUPPORTED: !libcpp-has-legacy-debug-mode, c++03
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

#include <list>

#include "check_assertion.h"

int main(int, char**) {
    std::list<int> v1(3);
    std::list<int> v2(3);
    int i = 4;
    TEST_LIBCPP_ASSERT_FAILURE(v1.insert(v2.begin(), i),
                               "list::insert(iterator, x) called with an iterator not referring to this list");

    return 0;
}

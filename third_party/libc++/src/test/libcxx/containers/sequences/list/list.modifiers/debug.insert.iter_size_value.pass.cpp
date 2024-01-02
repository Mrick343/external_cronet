//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <list>

// iterator insert(const_iterator position, size_type n, const value_type& x);

// REQUIRES: has-unix-headers
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// UNSUPPORTED: !libcpp-has-debug-mode, c++03
=======
// UNSUPPORTED: !libcpp-has-legacy-debug-mode, c++03
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

#include <list>

#include "check_assertion.h"

int main(int, char**) {
    std::list<int> c1(100);
    std::list<int> c2;
    TEST_LIBCPP_ASSERT_FAILURE(c1.insert(c2.cbegin(), 5, 1),
                               "list::insert(iterator, n, x) called with an iterator not referring to this list");

    return 0;
}

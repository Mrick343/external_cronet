//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <unordered_set>

// Dereference non-dereferenceable iterator.

// REQUIRES: has-unix-headers
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// UNSUPPORTED: !libcpp-has-debug-mode, c++03
=======
// UNSUPPORTED: !libcpp-has-legacy-debug-mode, c++03
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

#include <unordered_set>

#include "check_assertion.h"
#include "min_allocator.h"

int main(int, char**) {
    {
        typedef int T;
        typedef std::unordered_multiset<T> C;
        C c(1);
        C::local_iterator i = c.end(0);
        TEST_LIBCPP_ASSERT_FAILURE(
            *i, "Attempted to dereference a non-dereferenceable unordered container const_local_iterator");
    }

    {
        typedef int T;
        typedef std::unordered_multiset<T, std::hash<T>, std::equal_to<T>, min_allocator<T>> C;
        C c(1);
        C::local_iterator i = c.end(0);
        TEST_LIBCPP_ASSERT_FAILURE(
            *i, "Attempted to dereference a non-dereferenceable unordered container const_local_iterator");
    }

    return 0;
}

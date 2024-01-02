//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <list>

// Call erase(const_iterator position) with end()

// REQUIRES: has-unix-headers
// UNSUPPORTED: c++03
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -D_LIBCPP_ENABLE_ASSERTIONS=1
=======
// UNSUPPORTED: libcpp-hardening-mode=unchecked
// XFAIL: availability-verbose_abort-missing
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

#include <list>

#include "check_assertion.h"

int main(int, char**) {
    int a1[] = {1, 2, 3};
    std::list<int> l1(a1, a1+3);
    std::list<int>::const_iterator i = l1.end();
    TEST_LIBCPP_ASSERT_FAILURE(l1.erase(i), "list::erase(iterator) called with a non-dereferenceable iterator");

    return 0;
}

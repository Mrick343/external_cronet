//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <unordered_map>

// template <class P,
//           class = typename enable_if<is_convertible<P, value_type>::value>::type>
//     iterator insert(const_iterator p, P&& x);

// REQUIRES: has-unix-headers
<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// UNSUPPORTED: !libcpp-has-debug-mode, c++03
=======
// UNSUPPORTED: !libcpp-has-legacy-debug-mode, c++03
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)

#include <unordered_map>

#include "check_assertion.h"

int main(int, char**) {
    typedef std::unordered_multimap<double, int> C;
    typedef C::value_type P;
    C c;
    C c2;
    C::const_iterator e = c2.end();
    TEST_LIBCPP_ASSERT_FAILURE(
        c.insert(e, P(3.5, 3)),
        "unordered container::emplace_hint(const_iterator, args...) called with an iterator not referring to this unordered container");

    return 0;
}

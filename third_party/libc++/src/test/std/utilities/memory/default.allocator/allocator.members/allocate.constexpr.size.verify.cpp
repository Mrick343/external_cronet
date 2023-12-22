//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <memory>

// allocator:
// constexpr T* allocate(size_type n);

// UNSUPPORTED: c++03, c++11, c++14, c++17

#include <cstddef>
#include <memory>

#include "test_macros.h"

template <typename T>
constexpr bool test()
{
    typedef std::allocator<T> A;
    typedef std::allocator_traits<A> AT;
    A a;
    TEST_IGNORE_NODISCARD a.allocate(AT::max_size(a) + 1);           // just barely too large
    TEST_IGNORE_NODISCARD a.allocate(AT::max_size(a) * 2);           // significantly too large
    TEST_IGNORE_NODISCARD a.allocate(((std::size_t) -1) / sizeof(T) + 1); // multiply will overflow
    TEST_IGNORE_NODISCARD a.allocate((std::size_t) -1);                   // way too large

    return true;
}

void f() {
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
    static_assert(test<double>()); // expected-error-re {{{{(static_assert|static assertion)}} expression is not an integral constant expression}}
    LIBCPP_STATIC_ASSERT(test<const double>()); // expected-error-re {{{{(static_assert|static assertion)}} expression is not an integral constant expression}}
=======
    static_assert(test<double>()); // expected-error {{static assertion expression is not an integral constant expression}}
    LIBCPP_STATIC_ASSERT(test<const double>()); // expected-error {{static assertion expression is not an integral constant expression}}
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
}

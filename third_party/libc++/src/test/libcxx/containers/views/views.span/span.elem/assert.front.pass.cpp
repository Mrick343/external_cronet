//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//
// UNSUPPORTED: c++03, c++11, c++14, c++17

// <span>
//
// constexpr reference front() const noexcept;

// Make sure that accessing a span out-of-bounds triggers an assertion.

// REQUIRES: has-unix-headers
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -D_LIBCPP_ENABLE_ASSERTIONS=1
=======
// UNSUPPORTED: libcpp-hardening-mode=unchecked
// XFAIL: availability-verbose_abort-missing
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

#include <array>
#include <span>

#include "check_assertion.h"

int main(int, char**) {
    {
        std::array<int, 3> array{0, 1, 2};
        std::span<int> const s(array.data(), 0);
        TEST_LIBCPP_ASSERT_FAILURE(s.front(), "span<T>::front() on empty span");
    }

    {
        std::array<int, 3> array{0, 1, 2};
        std::span<int, 0> const s(array.data(), 0);
        TEST_LIBCPP_ASSERT_FAILURE(s.front(), "span<T, N>::front() on empty span");
    }

    return 0;
}

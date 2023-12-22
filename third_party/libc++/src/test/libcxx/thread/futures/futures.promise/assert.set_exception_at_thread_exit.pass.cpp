//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// REQUIRES: has-unix-headers
// UNSUPPORTED: c++03, no-threads
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// XFAIL: availability-verbose_abort-missing
// ADDITIONAL_COMPILE_FLAGS: -D_LIBCPP_ENABLE_ASSERTIONS=1
=======
// REQUIRES: libcpp-hardening-mode={{safe|debug}}
// XFAIL: availability-verbose_abort-missing
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

// <future>

// class promise<R>

// void set_exception_on_thread_exit(exception_ptr p);
// Test that a null exception_ptr is diagnosed.

#include <future>
#include <exception>

#include "check_assertion.h"

int main(int, char**) {
    {
        typedef int T;
        std::promise<T> p;
        TEST_LIBCPP_ASSERT_FAILURE(p.set_exception_at_thread_exit(std::exception_ptr()), "promise::set_exception_at_thread_exit: received nullptr");
    }

    {
        typedef int& T;
        std::promise<T> p;
        TEST_LIBCPP_ASSERT_FAILURE(p.set_exception_at_thread_exit(std::exception_ptr()), "promise::set_exception_at_thread_exit: received nullptr");
    }

    return 0;
}

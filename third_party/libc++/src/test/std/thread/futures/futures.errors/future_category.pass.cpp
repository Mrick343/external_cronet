//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//
//
// UNSUPPORTED: no-threads

// <future>

// const error_category& future_category();

#include <future>
#include <cstring>
#include <cassert>

#include "test_macros.h"

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
int main(int, char**)
{
    const std::error_category& ec = std::future_category();
    assert(std::strcmp(ec.name(), "future") == 0);

  return 0;
=======
// See https://llvm.org/D65667
struct StaticInit {
    const std::error_category* ec;
    ~StaticInit() {
        assert(std::strcmp(ec->name(), "future") == 0);
    }
};
static StaticInit foo;

int main(int, char**)
{
    {
        const std::error_category& ec = std::future_category();
        assert(std::strcmp(ec.name(), "future") == 0);
    }

    {
        foo.ec = &std::future_category();
        assert(std::strcmp(foo.ec->name(), "future") == 0);
    }

    return 0;
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
}

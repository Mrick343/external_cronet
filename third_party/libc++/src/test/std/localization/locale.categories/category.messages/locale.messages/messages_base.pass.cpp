//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <locale>

// class messages_base
// {
// public:
//     typedef unspecified catalog;
// };

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
#include <locale>
#include <type_traits>

#include "test_macros.h"

int main(int, char**)
{
    std::messages_base mb;
=======
#include <cstdint>
#include <locale>
#include <type_traits>

#include "assert_macros.h"

#ifdef _LIBCPP_VERSION
ASSERT_SAME_TYPE(std::messages_base::catalog, std::intptr_t);
#endif

// Check that we implement LWG2028
static_assert(std::is_signed<std::messages_base::catalog>::value, "");
static_assert(std::is_integral<std::messages_base::catalog>::value, "");

int main(int, char**) {
  std::messages_base mb;
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

  return 0;
}

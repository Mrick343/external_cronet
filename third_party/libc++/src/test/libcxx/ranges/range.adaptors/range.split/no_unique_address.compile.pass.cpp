//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: c++03, c++11, c++14, c++17
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)

// clang-cl and cl currently don't support [[no_unique_address]]
// XFAIL: msvc
=======
// XFAIL: msvc && (clang-16 || clang-17)
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

// class split_view {
//   _LIBCPP_NO_UNIQUE_ADDRESS _View __base_ = _View();
//   _LIBCPP_NO_UNIQUE_ADDRESS _Pattern __pattern_ = _Pattern();
// };

#include <ranges>

#include "test_iterators.h"

struct EmptyView : std::ranges::view_base {
  int* begin() const;
  int* end() const;
};

using SplitView = std::ranges::split_view<EmptyView, EmptyView>;
static_assert(sizeof(SplitView) == sizeof(std::ranges::__non_propagating_cache<std::ranges::subrange<int*>>));

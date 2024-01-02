//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: no-localization
// UNSUPPORTED: c++03, c++11, c++14, c++17
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)

// clang-cl and cl currently don't support [[no_unique_address]]
// XFAIL: msvc
=======
// XFAIL: msvc && (clang-16 || clang-17)
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

// Test the libc++ extension that the value stored in `std::ranges::istream_view` has been marked
// as _LIBCPP_NO_UNIQUE_ADDRESS

#include <istream>
#include <ranges>

struct Empty {
  friend std::istream& operator>>(std::istream& i, Empty const&) { return i; }
};

static_assert(sizeof(std::ranges::istream_view<Empty>) == sizeof(void*));


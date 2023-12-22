//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: !stdlib=libc++ && (c++03 || c++11 || c++14)

// <string_view>

// template<class charT, class traits, class Allocator>
//   basic_ostream<charT, traits>&
//   operator<<(basic_ostream<charT, traits>& os,
//              const basic_string_view<charT,traits> str);

#include <iosfwd>
#include <string_view>
#include <utility>

template <class SV, class = void>
struct HasDecl : std::false_type {};
template <class SV>
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
struct HasDecl<SV, decltype(static_cast<void>(std::declval<std::ostream&>() << std::declval<SV&>()))> : std::true_type {};
=======
struct HasDecl<SV, decltype(static_cast<void>(std::declval<std::ostream&>() << std::declval<SV&>()))> : std::true_type {
};
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

static_assert(HasDecl<std::string_view>::value, "streaming operator declaration not present");

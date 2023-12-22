//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>
//   The strings's value type must be the same as the traits's char_type

// UNSUPPORTED: no-wide-characters

#include <string>

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
std::basic_string<char, std::char_traits<wchar_t> > s; // expected-error@*:* {{traits_type::char_type must be the same type as CharT}}
=======
std::basic_string<char, std::char_traits<wchar_t> >
    s; // expected-error@*:* {{traits_type::char_type must be the same type as CharT}}
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

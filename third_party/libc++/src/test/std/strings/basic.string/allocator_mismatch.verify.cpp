//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>
//   The container's value type must be the same as the allocator's value type

#include <string>

<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
std::basic_string<char, std::char_traits<char>, std::allocator<int> > s; // expected-error@*:* {{Allocator::value_type must be same type as value_type}}
=======
std::basic_string<char, std::char_traits<char>, std::allocator<int> >
    s; // expected-error@*:* {{Allocator::value_type must be same type as value_type}}
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

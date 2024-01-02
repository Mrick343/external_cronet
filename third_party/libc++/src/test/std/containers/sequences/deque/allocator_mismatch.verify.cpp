//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <deque>
//   The container's value type must be the same as the allocator's value type

#include <deque>

std::deque<int, std::allocator<long> > d;
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
  // expected-error-re@*:* {{{{(static_assert|static assertion)}} failed{{.*}}Allocator::value_type must be same type as value_type}}
=======
  // expected-error-re@*:* {{static assertion failed{{.*}}Allocator::value_type must be same type as value_type}}
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

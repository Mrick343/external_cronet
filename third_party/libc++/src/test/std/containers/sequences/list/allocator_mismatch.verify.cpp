//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <list>
//   The container's value type must be the same as the allocator's value type

#include <list>

std::list<int, std::allocator<long> > l;
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
  // expected-error-re@*:* {{{{(static_assert|static assertion)}} failed{{.*}}Allocator::value_type must be same type as value_type}}
=======
  // expected-error-re@*:* {{static assertion failed{{.*}}Allocator::value_type must be same type as value_type}}
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

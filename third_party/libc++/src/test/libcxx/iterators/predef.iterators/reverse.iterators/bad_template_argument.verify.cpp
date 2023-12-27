//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: c++03, c++11, c++14, c++17

// <iterator>

// reverse_iterator

#include <iterator>

#include "test_iterators.h"

void f() {
  using BadIter = std::reverse_iterator<forward_iterator<int*>>;
<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
  BadIter i; //expected-error-re@*:* {{{{(static_assert|static assertion)}} failed{{.*}}reverse_iterator<It> requires It to be a bidirectional iterator.}}
=======
  BadIter i; //expected-error-re@*:* {{static assertion failed{{.*}}reverse_iterator<It> requires It to be a bidirectional iterator.}}
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)
}

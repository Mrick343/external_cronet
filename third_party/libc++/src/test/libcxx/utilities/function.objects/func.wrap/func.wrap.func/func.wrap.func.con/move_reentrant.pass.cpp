//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: c++03

// <functional>

// class function<R(ArgTypes...)>

// function& operator=(function &&);

#include <functional>
#include <cassert>

#include "test_macros.h"

struct A
{
  static std::function<void()> global;
  static bool cancel;

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
=======
  A() = default;
  A(const A&) = default;
  A& operator=(const A&) = default;
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
  ~A() {
    DoNotOptimize(cancel);
    if (cancel)
      global = std::function<void()>(nullptr);
  }
  void operator()() {}
};

std::function<void()> A::global;
bool A::cancel = false;

int main(int, char**)
{
  A::global = A();
  RTTI_ASSERT(A::global.target<A>());

  // Check that we don't recurse in A::~A().
  A::cancel = true;
  A::global = std::function<void()>(nullptr);
  RTTI_ASSERT(!A::global.target<A>());

  return 0;
}

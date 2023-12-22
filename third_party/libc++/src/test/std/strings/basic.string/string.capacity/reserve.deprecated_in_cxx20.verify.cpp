//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <string>

// void reserve(); // Deprecated in C++20

// UNSUPPORTED: c++03, c++11, c++14, c++17

#include <string>

void f() {
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
    std::string s;
    s.reserve(); // expected-warning {{'reserve' is deprecated}}
=======
  std::string s;
  s.reserve(); // expected-warning {{'reserve' is deprecated}}
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
}

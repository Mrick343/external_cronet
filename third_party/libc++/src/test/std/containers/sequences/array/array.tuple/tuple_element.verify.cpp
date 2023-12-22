//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <array>

// tuple_element<I, array<T, N> >::type

#include <array>
#include <cassert>

typedef double T;
typedef std::array<T, 3> C;
std::tuple_element<3, C> foo; // expected-note {{requested here}}
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// expected-error-re@array:* {{{{(static_assert|static assertion)}} failed{{( due to requirement '3U[L]{0,2} < 3U[L]{0,2}')?}}{{.*}}Index out of bounds in std::tuple_element<> (std::array)}}
=======
// expected-error-re@array:* {{static assertion failed{{( due to requirement '3U[L]{0,2} < 3U[L]{0,2}')?}}{{.*}}Index out of bounds in std::tuple_element<> (std::array)}}
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

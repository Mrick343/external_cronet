//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <tuple>

// template <class... Types> class tuple;

// template <size_t I, class... Types>
// struct tuple_element<I, tuple<Types...> >
// {
//     typedef Ti type;
// };

// UNSUPPORTED: c++03

#include <tuple>

using T =  std::tuple<int, long, void*>;
using E1 = typename std::tuple_element<1, T &>::type; // expected-error{{undefined template}}
using E2 = typename std::tuple_element<3, T>::type;
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
using E3 = typename std::tuple_element<4, T const>::type; // expected-error-re@*:* 2 {{{{(static_assert|static assertion)}} failed}}
=======
using E3 = typename std::tuple_element<4, T const>::type; // expected-error@*:* 2 {{static assertion failed}}
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

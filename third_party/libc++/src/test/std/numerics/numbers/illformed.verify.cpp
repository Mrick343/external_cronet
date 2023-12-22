//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: c++03, c++11, c++14, c++17

#include <numbers>

// Initializing the primary template is ill-formed.
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
int log2e{std::numbers::log2e_v<int>}; // expected-error-re@numbers:* {{{{(static_assert|static assertion)}} failed{{.*}}A program that instantiates a primary template of a mathematical constant variable template is ill-formed.}}
=======
int log2e{std::numbers::log2e_v<int>}; // expected-error-re@numbers:* {{static assertion failed{{.*}}A program that instantiates a primary template of a mathematical constant variable template is ill-formed.}}
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
int log10e{std::numbers::log10e_v<int>};
int pi{std::numbers::pi_v<int>};
int inv_pi{std::numbers::inv_pi_v<int>};
int inv_sqrtpi{std::numbers::inv_sqrtpi_v<int>};
int ln2{std::numbers::ln2_v<int>};
int ln10{std::numbers::ln10_v<int>};
int sqrt2{std::numbers::sqrt2_v<int>};
int sqrt3{std::numbers::sqrt3_v<int>};
int inv_sqrt3{std::numbers::inv_sqrt3_v<int>};
int egamma{std::numbers::egamma_v<int>};
int phi{std::numbers::phi_v<int>};

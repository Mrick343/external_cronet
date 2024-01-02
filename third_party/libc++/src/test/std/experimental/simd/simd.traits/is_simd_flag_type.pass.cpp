//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: c++03, c++11, c++14

// <experimental/simd>
//
// [simd.traits]
// template <class T> struct is_simd_flag_type;
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// template <class T> inline constexpr bool ex::is_simd_flag_type_v =
// ex::is_simd_flag_type<T>::value;

#include <cstdint>
#include <experimental/simd>
#include "test_macros.h"

namespace ex = std::experimental::parallelism_v2;

struct UserType {};

static_assert(ex::is_simd_flag_type<ex::element_aligned_tag>::value, "");
static_assert(ex::is_simd_flag_type<ex::vector_aligned_tag>::value, "");
static_assert(ex::is_simd_flag_type<ex::overaligned_tag<16>>::value, "");
static_assert(ex::is_simd_flag_type<ex::overaligned_tag<32>>::value, "");

static_assert(!ex::is_simd_flag_type<void>::value, "");
static_assert(!ex::is_simd_flag_type<int>::value, "");
static_assert(!ex::is_simd_flag_type<float>::value, "");
static_assert(!ex::is_simd_flag_type<UserType>::value, "");
static_assert(!ex::is_simd_flag_type<ex::simd<std::int8_t>>::value, "");
static_assert(!ex::is_simd_flag_type<ex::simd_mask<std::int8_t>>::value, "");

static_assert(ex::is_simd_flag_type_v<ex::element_aligned_tag>, "");
static_assert(ex::is_simd_flag_type_v<ex::vector_aligned_tag>, "");
static_assert(ex::is_simd_flag_type_v<ex::overaligned_tag<16>>, "");
static_assert(ex::is_simd_flag_type_v<ex::overaligned_tag<32>>, "");

static_assert(!ex::is_simd_flag_type_v<void>, "");
static_assert(!ex::is_simd_flag_type_v<int>, "");
static_assert(!ex::is_simd_flag_type_v<float>, "");
static_assert(!ex::is_simd_flag_type_v<UserType>, "");
static_assert(!ex::is_simd_flag_type_v<ex::simd<std::int8_t>>, "");
static_assert(!ex::is_simd_flag_type_v<ex::simd_mask<std::int8_t>>, "");

int main(int, char**) {
=======
// template <class T> inline constexpr bool ex::is_simd_flag_type_v = ex::is_simd_flag_type<T>::value;

#include "../test_utils.h"

namespace ex = std::experimental::parallelism_v2;

template <class T, std::size_t N>
struct CheckIsSimdFlagType {
  template <class SimdAbi>
  void operator()() {
    static_assert(ex::is_simd_flag_type<ex::element_aligned_tag>::value);
    static_assert(ex::is_simd_flag_type<ex::vector_aligned_tag>::value);
    static_assert(ex::is_simd_flag_type<ex::overaligned_tag<N>>::value);

    static_assert(!ex::is_simd_flag_type<T>::value);
    static_assert(!ex::is_simd_flag_type<ex::simd<T, SimdAbi>>::value);
    static_assert(!ex::is_simd_flag_type<ex::simd_mask<T, SimdAbi>>::value);

    static_assert(ex::is_simd_flag_type_v<ex::element_aligned_tag>);
    static_assert(ex::is_simd_flag_type_v<ex::vector_aligned_tag>);
    static_assert(ex::is_simd_flag_type_v<ex::overaligned_tag<N>>);

    static_assert(!ex::is_simd_flag_type_v<T>);
    static_assert(!ex::is_simd_flag_type_v<ex::simd<T, SimdAbi>>);
    static_assert(!ex::is_simd_flag_type_v<ex::simd_mask<T, SimdAbi>>);
  }
};

int main(int, char**) {
  test_all_simd_abi<CheckIsSimdFlagType>();
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)
  return 0;
}

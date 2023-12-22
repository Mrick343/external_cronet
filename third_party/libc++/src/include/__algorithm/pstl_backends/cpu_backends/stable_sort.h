//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

#ifndef _LIBCPP___ALGORITHM_PSTL_BACKENDS_CPU_BACKENDS_STABLE_SORT_H
#define _LIBCPP___ALGORITHM_PSTL_BACKENDS_CPU_BACKENDS_STABLE_SORT_H

#include <__algorithm/pstl_backends/cpu_backends/backend.h>
#include <__algorithm/stable_sort.h>
#include <__config>
#include <__type_traits/is_execution_policy.h>
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
#include <__utility/terminate_on_exception.h>

#if !defined(_LIBCPP_HAS_NO_PRAGMA_SYSTEM_HEADER)
#  pragma GCC system_header
#endif

#if !defined(_LIBCPP_HAS_NO_INCOMPLETE_PSTL) && _LIBCPP_STD_VER >= 17

_LIBCPP_BEGIN_NAMESPACE_STD

template <class _ExecutionPolicy, class _RandomAccessIterator, class _Comp>
_LIBCPP_HIDE_FROM_ABI void
__pstl_stable_sort(__cpu_backend_tag, _RandomAccessIterator __first, _RandomAccessIterator __last, _Comp __comp) {
  if constexpr (__is_parallel_execution_policy_v<_ExecutionPolicy>) {
    std::__terminate_on_exception([&] {
      __par_backend::__parallel_stable_sort(
          __first, __last, __comp, [](_RandomAccessIterator __g_first, _RandomAccessIterator __g_last, _Comp __g_comp) {
            std::stable_sort(__g_first, __g_last, __g_comp);
          });
    });
  } else {
    std::stable_sort(__first, __last, __comp);
=======
#include <__utility/empty.h>
#include <optional>

#if !defined(_LIBCPP_HAS_NO_PRAGMA_SYSTEM_HEADER)
#  pragma GCC system_header
#endif

#if !defined(_LIBCPP_HAS_NO_INCOMPLETE_PSTL) && _LIBCPP_STD_VER >= 17

_LIBCPP_BEGIN_NAMESPACE_STD

template <class _ExecutionPolicy, class _RandomAccessIterator, class _Comp>
_LIBCPP_HIDE_FROM_ABI optional<__empty>
__pstl_stable_sort(__cpu_backend_tag, _RandomAccessIterator __first, _RandomAccessIterator __last, _Comp __comp) {
  if constexpr (__is_parallel_execution_policy_v<_ExecutionPolicy>) {
    return __par_backend::__parallel_stable_sort(
        __first, __last, __comp, [](_RandomAccessIterator __g_first, _RandomAccessIterator __g_last, _Comp __g_comp) {
          std::stable_sort(__g_first, __g_last, __g_comp);
        });
  } else {
    std::stable_sort(__first, __last, __comp);
    return __empty{};
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
  }
}

_LIBCPP_END_NAMESPACE_STD

#endif // !defined(_LIBCPP_HAS_NO_INCOMPLETE_PSTL) && _LIBCPP_STD_VER >= 17

#endif // _LIBCPP___ALGORITHM_PSTL_BACKENDS_CPU_BACKENDS_STABLE_SORT_H

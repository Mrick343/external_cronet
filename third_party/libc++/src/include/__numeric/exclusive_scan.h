// -*- C++ -*-
//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

#ifndef _LIBCPP___NUMERIC_EXCLUSIVE_SCAN_H
#define _LIBCPP___NUMERIC_EXCLUSIVE_SCAN_H

#include <__config>
#include <__functional/operations.h>
#include <__utility/move.h>

#if !defined(_LIBCPP_HAS_NO_PRAGMA_SYSTEM_HEADER)
#  pragma GCC system_header
#endif

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
_LIBCPP_BEGIN_NAMESPACE_STD

#if _LIBCPP_STD_VER >= 17

template <class _InputIterator, class _OutputIterator, class _Tp, class _BinaryOp>
_LIBCPP_INLINE_VISIBILITY _LIBCPP_CONSTEXPR_SINCE_CXX20 _OutputIterator
exclusive_scan(_InputIterator __first, _InputIterator __last, _OutputIterator __result, _Tp __init, _BinaryOp __b) {
  if (__first != __last) {
    _Tp __tmp(__b(__init, *__first));
    while (true) {
      *__result = _VSTD::move(__init);
      ++__result;
      ++__first;
      if (__first == __last)
        break;
      __init = _VSTD::move(__tmp);
      __tmp = __b(__init, *__first);
    }
  }
  return __result;
}

template <class _InputIterator, class _OutputIterator, class _Tp>
_LIBCPP_INLINE_VISIBILITY _LIBCPP_CONSTEXPR_SINCE_CXX20 _OutputIterator
exclusive_scan(_InputIterator __first, _InputIterator __last, _OutputIterator __result, _Tp __init) {
  return _VSTD::exclusive_scan(__first, __last, __result, __init, _VSTD::plus<>());
}

#endif // _LIBCPP_STD_VER >= 17

_LIBCPP_END_NAMESPACE_STD
=======
_LIBCPP_PUSH_MACROS
#include <__undef_macros>

_LIBCPP_BEGIN_NAMESPACE_STD

#if _LIBCPP_STD_VER >= 17

template <class _InputIterator, class _OutputIterator, class _Tp, class _BinaryOp>
_LIBCPP_INLINE_VISIBILITY _LIBCPP_CONSTEXPR_SINCE_CXX20 _OutputIterator
exclusive_scan(_InputIterator __first, _InputIterator __last, _OutputIterator __result, _Tp __init, _BinaryOp __b) {
  if (__first != __last) {
    _Tp __tmp(__b(__init, *__first));
    while (true) {
      *__result = _VSTD::move(__init);
      ++__result;
      ++__first;
      if (__first == __last)
        break;
      __init = _VSTD::move(__tmp);
      __tmp = __b(__init, *__first);
    }
  }
  return __result;
}

template <class _InputIterator, class _OutputIterator, class _Tp>
_LIBCPP_INLINE_VISIBILITY _LIBCPP_CONSTEXPR_SINCE_CXX20 _OutputIterator
exclusive_scan(_InputIterator __first, _InputIterator __last, _OutputIterator __result, _Tp __init) {
  return _VSTD::exclusive_scan(__first, __last, __result, __init, _VSTD::plus<>());
}

#endif // _LIBCPP_STD_VER >= 17

_LIBCPP_END_NAMESPACE_STD

_LIBCPP_POP_MACROS
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

#endif // _LIBCPP___NUMERIC_EXCLUSIVE_SCAN_H

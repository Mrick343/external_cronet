//===---------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===---------------------------------------------------------------------===//

#ifndef _LIBCPP___FWD_SUBRANGE_H
#define _LIBCPP___FWD_SUBRANGE_H

#include <__config>

#if !defined(_LIBCPP_HAS_NO_PRAGMA_SYSTEM_HEADER)
#  pragma GCC system_header
#endif

#if _LIBCPP_STD_VER >= 20

#include <__iterator/concepts.h>

_LIBCPP_BEGIN_NAMESPACE_STD

namespace ranges {

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
enum class _LIBCPP_ENUM_VIS subrange_kind : bool { unsized, sized };
=======
enum class subrange_kind : bool { unsized, sized };
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

template <input_or_output_iterator _Iter, sentinel_for<_Iter> _Sent, subrange_kind _Kind>
  requires(_Kind == subrange_kind::sized || !sized_sentinel_for<_Sent, _Iter>)
class _LIBCPP_TEMPLATE_VIS subrange;

} // namespace ranges

_LIBCPP_END_NAMESPACE_STD

#endif // _LIBCPP_STD_VER >= 20

#endif // _LIBCPP___FWD_SUBRANGE_H

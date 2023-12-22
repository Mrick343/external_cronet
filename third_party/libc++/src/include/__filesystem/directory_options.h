// -*- C++ -*-
//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

#ifndef _LIBCPP___FILESYSTEM_DIRECTORY_OPTIONS_H
#define _LIBCPP___FILESYSTEM_DIRECTORY_OPTIONS_H

#include <__availability>
#include <__config>

#if !defined(_LIBCPP_HAS_NO_PRAGMA_SYSTEM_HEADER)
#  pragma GCC system_header
#endif

#ifndef _LIBCPP_CXX03_LANG

_LIBCPP_BEGIN_NAMESPACE_FILESYSTEM

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
enum class _LIBCPP_ENUM_VIS directory_options : unsigned char {
=======
enum class directory_options : unsigned char {
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
  none = 0,
  follow_directory_symlink = 1,
  skip_permission_denied = 2
};

_LIBCPP_INLINE_VISIBILITY
inline constexpr directory_options operator&(directory_options __lhs,
                                             directory_options __rhs) {
  return static_cast<directory_options>(static_cast<unsigned char>(__lhs) &
                                        static_cast<unsigned char>(__rhs));
}

_LIBCPP_INLINE_VISIBILITY
inline constexpr directory_options operator|(directory_options __lhs,
                                             directory_options __rhs) {
  return static_cast<directory_options>(static_cast<unsigned char>(__lhs) |
                                        static_cast<unsigned char>(__rhs));
}

_LIBCPP_INLINE_VISIBILITY
inline constexpr directory_options operator^(directory_options __lhs,
                                             directory_options __rhs) {
  return static_cast<directory_options>(static_cast<unsigned char>(__lhs) ^
                                        static_cast<unsigned char>(__rhs));
}

_LIBCPP_INLINE_VISIBILITY
inline constexpr directory_options operator~(directory_options __lhs) {
  return static_cast<directory_options>(~static_cast<unsigned char>(__lhs));
}

_LIBCPP_INLINE_VISIBILITY
inline directory_options& operator&=(directory_options& __lhs,
                                     directory_options __rhs) {
  return __lhs = __lhs & __rhs;
}

_LIBCPP_INLINE_VISIBILITY
inline directory_options& operator|=(directory_options& __lhs,
                                     directory_options __rhs) {
  return __lhs = __lhs | __rhs;
}

_LIBCPP_INLINE_VISIBILITY
inline directory_options& operator^=(directory_options& __lhs,
                                     directory_options __rhs) {
  return __lhs = __lhs ^ __rhs;
}

_LIBCPP_END_NAMESPACE_FILESYSTEM

#endif // _LIBCPP_CXX03_LANG

#endif // _LIBCPP___FILESYSTEM_DIRECTORY_OPTIONS_H

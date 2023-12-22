//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
#include <cstdlib>
#include <new>

namespace std { // purposefully not versioned

#ifndef __GLIBCXX__
const nothrow_t nothrow{};
#endif

#ifndef LIBSTDCXX

void __throw_bad_alloc() {
#  ifndef _LIBCPP_HAS_NO_EXCEPTIONS
  throw bad_alloc();
#  else
  std::abort();
=======
#include <__verbose_abort>
#include <new>

namespace std { // purposefully not versioned

#ifndef __GLIBCXX__
const nothrow_t nothrow{};
#endif

#ifndef LIBSTDCXX

void __throw_bad_alloc() {
#  ifndef _LIBCPP_HAS_NO_EXCEPTIONS
  throw bad_alloc();
#  else
  _LIBCPP_VERBOSE_ABORT("bad_alloc was thrown in -fno-exceptions mode");
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
#  endif
}

#endif // !LIBSTDCXX

} // namespace std

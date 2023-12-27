//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// test <setjmp.h>
<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)

#include <setjmp.h>

#include "test_macros.h"

#ifndef setjmp
#error setjmp not defined
#endif

jmp_buf jb;
ASSERT_SAME_TYPE(void, decltype(longjmp(jb, 0)));
=======
//
// Even though <setjmp.h> is not provided by libc++, we still test that
// using it with libc++ on the search path will work.

#include <setjmp.h>

#include "test_macros.h"

jmp_buf jb;
ASSERT_SAME_TYPE(void, decltype(longjmp(jb, 0)));

void f() { setjmp(jb); }
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)

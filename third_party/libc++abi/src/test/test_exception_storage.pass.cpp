//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
#include <algorithm>
#include <cstdio>
#include <cstdlib>
#include <__threading_support>
#include <unistd.h>

#include "../src/cxa_exception.h"

#include "test_macros.h"

typedef __cxxabiv1::__cxa_eh_globals globals_t ;

void *thread_code (void *parm) {
    size_t *result = (size_t *) parm;
    globals_t *glob1, *glob2;

    glob1 = __cxxabiv1::__cxa_get_globals ();
    if ( NULL == glob1 )
        std::printf("Got null result from __cxa_get_globals\n");

    glob2 = __cxxabiv1::__cxa_get_globals_fast ();
    if ( glob1 != glob2 )
        std::printf("Got different globals!\n");

    *result = (size_t) glob1;
#ifndef TEST_HAS_NO_THREADS
    sleep ( 1 );
#endif
    return parm;
}

#ifndef TEST_HAS_NO_THREADS
#define NUMTHREADS  10
size_t                 thread_globals [ NUMTHREADS ] = { 0 };
std::__libcpp_thread_t   threads        [ NUMTHREADS ];
#endif

int main() {
#ifndef TEST_HAS_NO_THREADS
//  Make the threads, let them run, and wait for them to finish
    for ( int i = 0; i < NUMTHREADS; ++i )
        std::__libcpp_thread_create ( threads + i, thread_code, (void *) (thread_globals + i));
    for ( int i = 0; i < NUMTHREADS; ++i )
        std::__libcpp_thread_join ( &threads [ i ] );

    int retVal = 0;
    for ( int i = 0; i < NUMTHREADS; ++i ) {
        if ( 0 == thread_globals [ i ] ) {
            std::printf("Thread #%d had a zero global\n", i);
            retVal = 1;
        }
    }

    std::sort ( thread_globals, thread_globals + NUMTHREADS );
    for ( int i = 1; i < NUMTHREADS; ++i ) {
        if ( thread_globals [ i - 1 ] == thread_globals [ i ] ) {
            std::printf("Duplicate thread globals (%d and %d)\n", i-1, i);
            retVal = 2;
        }
    }
    return retVal;
#else // TEST_HAS_NO_THREADS
    size_t thread_globals;
    thread_code(&thread_globals);
    // Check that __cxa_get_globals() is not NULL.
    return (thread_globals == 0) ? 1 : 0;
#endif // !TEST_HAS_NO_THREADS
=======
// UNSUPPORTED: c++03

#include "assert_macros.h"
#include "concat_macros.h"
#include "../src/cxa_exception.h"

int main(int, char**) {
  void* globals = __cxxabiv1::__cxa_get_globals();
  TEST_REQUIRE(globals != nullptr, TEST_WRITE_CONCATENATED("Got null result from __cxa_get_globals"));

  void* fast_globals = __cxxabiv1::__cxa_get_globals_fast();
  TEST_REQUIRE(globals == fast_globals, TEST_WRITE_CONCATENATED("__cxa_get_globals returned ", globals,
                                                                " but __cxa_get_globals_fast returned ", fast_globals));

  return 0;
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)
}

//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// UNSUPPORTED: no-exceptions

// This test checks that the compiler does not make incorrect assumptions
// about the alignment of the exception (only in that specific case, of
// course).
//
// There was a bug where Clang would emit a call to memset assuming a 16-byte
// aligned exception even when back-deploying to older Darwin systems where
// exceptions are 8-byte aligned, which caused a segfault on those systems.

struct exception {
    exception() : x(0) { }
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
=======
    exception(const exception&) = default;
    exception& operator=(const exception&) = default;
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)
    virtual ~exception() { }
    int x;
};

struct foo : exception { };

int main(int, char**) {
    try {
      throw foo();
    } catch (...) {

    }
    return 0;
}

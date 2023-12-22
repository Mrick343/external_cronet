//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// Test that we can set a custom verbose termination function at link-time.

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// ADDITIONAL_COMPILE_FLAGS: -D_LIBCPP_ENABLE_ASSERTIONS=1

=======
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
// We flag uses of the verbose termination function in older dylibs at compile-time to avoid runtime
// failures when back-deploying.
// XFAIL: availability-verbose_abort-missing

#include <cstdlib>

void std::__libcpp_verbose_abort(char const*, ...) {
  std::exit(EXIT_SUCCESS);
}

int main(int, char**) {
  _LIBCPP_ASSERT(false, "message");
  return EXIT_FAILURE;
}

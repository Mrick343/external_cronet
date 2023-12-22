#===----------------------------------------------------------------------===##
#
# Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
# See https://llvm.org/LICENSE.txt for license information.
# SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
#
#===----------------------------------------------------------------------===##

# Ensure that none of the standard C++ headers implicitly include cassert or
# assert.h (because assert() is implemented as a macro).

# RUN: %{python} %s %{libcxx}/utils

import sys
sys.path.append(sys.argv[1])
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
from libcxx.test.header_information import lit_header_restrictions, public_headers
=======
from libcxx.header_information import lit_header_restrictions, public_headers
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

for header in public_headers:
  if header == 'cassert':
    continue

  print(f"""\
//--- {header}.compile.pass.cpp
{lit_header_restrictions.get(header, '')}

#include <{header}>

#ifdef assert
# error "Do not include cassert or assert.h in standard header files"
#endif
""")

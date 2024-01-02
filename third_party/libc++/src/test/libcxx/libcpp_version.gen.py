#===----------------------------------------------------------------------===##
#
# Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
# See https://llvm.org/LICENSE.txt for license information.
# SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
#
#===----------------------------------------------------------------------===##

# Test that all headers define the _LIBCPP_VERSION macro.

# RUN: %{python} %s %{libcxx}/utils

import sys
sys.path.append(sys.argv[1])
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
from libcxx.test.header_information import lit_header_restrictions, public_headers
=======
from libcxx.header_information import lit_header_restrictions, public_headers
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

for header in public_headers:
  print(f"""\
//--- {header}.compile.pass.cpp
{lit_header_restrictions.get(header, '')}

#include <{header}>

#ifndef _LIBCPP_VERSION
# error <{header}> does not seem to define _LIBCPP_VERSION
#endif
""")

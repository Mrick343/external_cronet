#===----------------------------------------------------------------------===##
#
# Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
# See https://llvm.org/LICENSE.txt for license information.
# SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
#
#===----------------------------------------------------------------------===##

# Test that all public C++ headers define the verbose termination function, which
# is required for users to be able to include any public header and then override
# the function using a strong definition.

# RUN: %{python} %s %{libcxx}/utils

import sys
sys.path.append(sys.argv[1])
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
from libcxx.test.header_information import lit_header_restrictions, public_headers
=======
from libcxx.header_information import lit_header_restrictions, public_headers
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

for header in public_headers:
    # Skip C compatibility headers.
    if header.endswith('.h'):
        continue

    BLOCKLIT = '' # block Lit from interpreting a RUN/XFAIL/etc inside the generation script
    print(f"""\
//--- {header}.compile.pass.cpp
{lit_header_restrictions.get(header, '')}

// XFAIL{BLOCKLIT}: availability-verbose_abort-missing

#include <{header}>
using HandlerType = decltype(std::__libcpp_verbose_abort);
""")

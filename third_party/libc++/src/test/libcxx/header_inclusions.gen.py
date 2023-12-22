#===----------------------------------------------------------------------===##
#
# Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
# See https://llvm.org/LICENSE.txt for license information.
# SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
#
#===----------------------------------------------------------------------===##

# Test that all headers include all the other headers they're supposed to, as
# prescribed by the Standard.

# RUN: %{python} %s %{libcxx}/utils

import sys
sys.path.append(sys.argv[1])
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
from libcxx.test.header_information import lit_header_restrictions, public_headers, mandatory_inclusions
=======
from libcxx.header_information import lit_header_restrictions, public_headers, mandatory_inclusions
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

for header in public_headers:
  header_guard = lambda h: f"_LIBCPP_{h.upper().replace('.', '_').replace('/', '_')}"

  # <cassert> has no header guards
  if header == 'cassert':
    checks = ''
  else:
    checks = f'''
#ifndef {header_guard(header)}
# error <{header}> was expected to define a header guard {header_guard(header)}
#endif
'''
  for includee in mandatory_inclusions.get(header, []):
    checks += f'''
#ifndef {header_guard(includee)}
# error <{header}> was expected to include <{includee}>
#endif
'''

  print(f"""\
//--- {header}.compile.pass.cpp
{lit_header_restrictions.get(header, '')}

#include <{header}>
{checks}
""")

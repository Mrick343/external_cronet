//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <fstream>

// template <class charT, class traits = char_traits<charT> >
// class basic_fstream

// The char type of the stream and the char_type of the traits have to match

// UNSUPPORTED: no-wide-characters

#include <fstream>

std::basic_fstream<char, std::char_traits<wchar_t> > f;
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// expected-error-re@ios:* {{{{(static_assert|static assertion)}} failed{{.*}}traits_type::char_type must be the same type as CharT}}
// expected-error-re@streambuf:* {{{{(static_assert|static assertion)}} failed{{.*}}traits_type::char_type must be the same type as CharT}}
=======
// expected-error-re@ios:* {{static assertion failed{{.*}}traits_type::char_type must be the same type as CharT}}
// expected-error-re@streambuf:* {{static assertion failed{{.*}}traits_type::char_type must be the same type as CharT}}
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

// expected-error@fstream:* {{only virtual member functions can be marked 'override'}}
// expected-error@fstream:* {{only virtual member functions can be marked 'override'}}
// expected-error@fstream:* {{only virtual member functions can be marked 'override'}}
// expected-error@fstream:* {{only virtual member functions can be marked 'override'}}
// expected-error@fstream:* {{only virtual member functions can be marked 'override'}}
// expected-error@fstream:* {{only virtual member functions can be marked 'override'}}
// expected-error@fstream:* {{only virtual member functions can be marked 'override'}}
// expected-error@fstream:* {{only virtual member functions can be marked 'override'}}
// expected-error@fstream:* {{only virtual member functions can be marked 'override'}}
// expected-error@istream:* {{only virtual member functions can be marked 'override'}}
// expected-error@istream:* {{only virtual member functions can be marked 'override'}}

// FIXME: As of commit r324062 Clang incorrectly generates a diagnostic about mismatching
// exception specifications for types which are already invalid for one reason or another.
// For now we tolerate this diagnostic.
// expected-error@ostream:* 0-1 {{exception specification of overriding function is more lax than base version}}

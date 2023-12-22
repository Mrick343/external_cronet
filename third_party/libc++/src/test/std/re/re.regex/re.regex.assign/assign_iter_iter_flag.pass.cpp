//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

// <regex>

// template <class charT, class traits = regex_traits<charT>> class basic_regex;

// template <class InputIterator>
//    basic_regex&
//    assign(InputIterator first, InputIterator last,
//           flag_type f = regex_constants::ECMAScript);

<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
#include <regex>
#include <cassert>
=======
#include <cassert>
#include <regex>
#include <string>
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

#include "test_macros.h"
#include "test_iterators.h"

int main(int, char**)
{
    typedef cpp17_input_iterator<std::string::const_iterator> I;
    typedef forward_iterator<std::string::const_iterator> F;
    std::string s4("(a([bc]))");
    std::regex r2;

    r2.assign(I(s4.begin()), I(s4.end()));
    assert(r2.flags() == std::regex::ECMAScript);
    assert(r2.mark_count() == 2);

    r2.assign(I(s4.begin()), I(s4.end()), std::regex::extended);
    assert(r2.flags() == std::regex::extended);
    assert(r2.mark_count() == 2);

    r2.assign(F(s4.begin()), F(s4.end()));
    assert(r2.flags() == std::regex::ECMAScript);
    assert(r2.mark_count() == 2);

    r2.assign(F(s4.begin()), F(s4.end()), std::regex::extended);
    assert(r2.flags() == std::regex::extended);
    assert(r2.mark_count() == 2);

  return 0;
}

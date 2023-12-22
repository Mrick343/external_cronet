//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

#include <__config>
#include <fstream>
#include <ios>
#include <istream>
#include <ostream>
#include <sstream>
#include <streambuf>

_LIBCPP_BEGIN_NAMESPACE_STD

// Original explicit instantiations provided in the library
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_ios<char>;
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_streambuf<char>;
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_istream<char>;
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_ostream<char>;
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_iostream<char>;

#ifndef _LIBCPP_HAS_NO_WIDE_CHARACTERS
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_ios<wchar_t>;
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_streambuf<wchar_t>;
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_istream<wchar_t>;
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_ostream<wchar_t>;
#endif

// Additional instantiations added later. Whether programs rely on these being
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
// available is protected by _LIBCPP_ABI_ENABLE_ADDITIONAL_IOSTREAM_EXPLICIT_INSTANTIATIONS_1.
=======
// available is protected by _LIBCPP_AVAILABILITY_HAS_NO_ADDITIONAL_IOSTREAM_EXPLICIT_INSTANTIATIONS_1.
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_stringbuf<char>;
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_stringstream<char>;
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_ostringstream<char>;
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_istringstream<char>;

#ifndef _LIBCPP_HAS_NO_FILESYSTEM
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_ifstream<char>;
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_ofstream<char>;
template class _LIBCPP_CLASS_TEMPLATE_INSTANTIATION_VIS basic_filebuf<char>;
#endif

// Add more here if needed...

_LIBCPP_END_NAMESPACE_STD

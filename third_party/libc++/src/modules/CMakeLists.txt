if (CMAKE_VERSION VERSION_LESS 3.26)
  message(WARNING "The libc++ modules won't be available because the CMake version is too old. Update to CMake 3.26 or later.")
  return()
endif()

# The headers of Table 24: C++ library headers [tab:headers.cpp]
# and the headers of Table 25: C++ headers for C library facilities [tab:headers.cpp.c]
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
set(LIBCXX_SOURCES_MODULE_STD
  std.cppm
  std/algorithm.cppm
  std/any.cppm
  std/array.cppm
  std/atomic.cppm
  std/barrier.cppm
  std/bit.cppm
  std/bitset.cppm
  std/cassert.cppm
  std/cctype.cppm
  std/cerrno.cppm
  std/cfenv.cppm
  std/cfloat.cppm
  std/charconv.cppm
  std/chrono.cppm
  std/cinttypes.cppm
  std/climits.cppm
  std/clocale.cppm
  std/cmath.cppm
  std/codecvt.cppm
  std/compare.cppm
  std/complex.cppm
  std/concepts.cppm
  std/condition_variable.cppm
  std/coroutine.cppm
  std/csetjmp.cppm
  std/csignal.cppm
  std/cstdarg.cppm
  std/cstddef.cppm
  std/cstdint.cppm
  std/cstdio.cppm
  std/cstdlib.cppm
  std/cstring.cppm
  std/ctime.cppm
  std/cuchar.cppm
  std/cwchar.cppm
  std/cwctype.cppm
  std/deque.cppm
  std/exception.cppm
  std/execution.cppm
  std/expected.cppm
  std/filesystem.cppm
  std/flat_map.cppm
  std/flat_set.cppm
  std/format.cppm
  std/forward_list.cppm
  std/fstream.cppm
  std/functional.cppm
  std/future.cppm
  std/generator.cppm
  std/hazard_pointer.cppm
  std/initializer_list.cppm
  std/iomanip.cppm
  std/ios.cppm
  std/iosfwd.cppm
  std/iostream.cppm
  std/iostream.cppm
  std/istream.cppm
  std/iterator.cppm
  std/latch.cppm
  std/limits.cppm
  std/list.cppm
  std/locale.cppm
  std/map.cppm
  std/mdspan.cppm
  std/memory.cppm
  std/memory_resource.cppm
  std/mutex.cppm
  std/new.cppm
  std/numbers.cppm
  std/numeric.cppm
  std/optional.cppm
  std/ostream.cppm
  std/print.cppm
  std/queue.cppm
  std/random.cppm
  std/ranges.cppm
  std/ratio.cppm
  std/rcu.cppm
  std/regex.cppm
  std/scoped_allocator.cppm
  std/semaphore.cppm
  std/set.cppm
  std/shared_mutex.cppm
  std/source_location.cppm
  std/span.cppm
  std/spanstream.cppm
  std/sstream.cppm
  std/stack.cppm
  std/stacktrace.cppm
  std/stdexcept.cppm
  std/stdexcept.cppm
  std/stdfloat.cppm
  std/stop_token.cppm
  std/streambuf.cppm
  std/string.cppm
  std/string_view.cppm
  std/strstream.cppm
  std/syncstream.cppm
  std/system_error.cppm
  std/text_encoding.cppm
  std/thread.cppm
  std/tuple.cppm
  std/type_traits.cppm
  std/typeindex.cppm
  std/typeinfo.cppm
  std/unordered_map.cppm
  std/unordered_set.cppm
  std/utility.cppm
  std/valarray.cppm
  std/variant.cppm
  std/vector.cppm
  std/version.cppm
)

# TODO MODULES the CMakeLists.txt in the install directory is only temporary
# When that is removed the configured file can use the substitution
# LIBCXX_GENERATED_INCLUDE_TARGET_DIR avoiding this set.
# Also clean up the parts needed to generate the install version.
set(LIBCXX_CONFIGURED_INCLUDE_DIR ${LIBCXX_GENERATED_INCLUDE_TARGET_DIR})
configure_file(
  "CMakeLists.txt.in"
  "${LIBCXX_GENERATED_MODULE_DIR}/CMakeLists.txt"
  @ONLY
)

set(_all_modules "${LIBCXX_GENERATED_MODULE_DIR}/CMakeLists.txt")
foreach(file ${LIBCXX_SOURCES_MODULE_STD})
=======
set(LIBCXX_MODULE_STD_SOURCES
  std/algorithm.inc
  std/any.inc
  std/array.inc
  std/atomic.inc
  std/barrier.inc
  std/bit.inc
  std/bitset.inc
  std/cassert.inc
  std/cctype.inc
  std/cerrno.inc
  std/cfenv.inc
  std/cfloat.inc
  std/charconv.inc
  std/chrono.inc
  std/cinttypes.inc
  std/climits.inc
  std/clocale.inc
  std/cmath.inc
  std/codecvt.inc
  std/compare.inc
  std/complex.inc
  std/concepts.inc
  std/condition_variable.inc
  std/coroutine.inc
  std/csetjmp.inc
  std/csignal.inc
  std/cstdarg.inc
  std/cstddef.inc
  std/cstdint.inc
  std/cstdio.inc
  std/cstdlib.inc
  std/cstring.inc
  std/ctime.inc
  std/cuchar.inc
  std/cwchar.inc
  std/cwctype.inc
  std/deque.inc
  std/exception.inc
  std/execution.inc
  std/expected.inc
  std/filesystem.inc
  std/flat_map.inc
  std/flat_set.inc
  std/format.inc
  std/forward_list.inc
  std/fstream.inc
  std/functional.inc
  std/future.inc
  std/generator.inc
  std/hazard_pointer.inc
  std/initializer_list.inc
  std/iomanip.inc
  std/ios.inc
  std/iosfwd.inc
  std/iostream.inc
  std/istream.inc
  std/iterator.inc
  std/latch.inc
  std/limits.inc
  std/list.inc
  std/locale.inc
  std/map.inc
  std/mdspan.inc
  std/memory.inc
  std/memory_resource.inc
  std/mutex.inc
  std/new.inc
  std/numbers.inc
  std/numeric.inc
  std/optional.inc
  std/ostream.inc
  std/print.inc
  std/queue.inc
  std/random.inc
  std/ranges.inc
  std/ratio.inc
  std/rcu.inc
  std/regex.inc
  std/scoped_allocator.inc
  std/semaphore.inc
  std/set.inc
  std/shared_mutex.inc
  std/source_location.inc
  std/span.inc
  std/spanstream.inc
  std/sstream.inc
  std/stack.inc
  std/stacktrace.inc
  std/stdexcept.inc
  std/stdfloat.inc
  std/stop_token.inc
  std/streambuf.inc
  std/string.inc
  std/string_view.inc
  std/strstream.inc
  std/syncstream.inc
  std/system_error.inc
  std/text_encoding.inc
  std/thread.inc
  std/tuple.inc
  std/type_traits.inc
  std/typeindex.inc
  std/typeinfo.inc
  std/unordered_map.inc
  std/unordered_set.inc
  std/utility.inc
  std/valarray.inc
  std/variant.inc
  std/vector.inc
  std/version.inc
)

# TODO MODULES the CMakeLists.txt in the install directory is only temporary
# When that is removed the configured file can use the substitution
# LIBCXX_GENERATED_INCLUDE_TARGET_DIR avoiding this set.
# Also clean up the parts needed to generate the install version.
# - LIBCXX_GENERATED_INCLUDE_DIR contains the libc++ headers
# - LIBCXX_GENERATED_INCLUDE_TARGET_DIR contains the libc++ site config
if ("${LIBCXX_GENERATED_INCLUDE_DIR}" STREQUAL "${LIBCXX_GENERATED_INCLUDE_TARGET_DIR}")
  # This typically happens when the target is not installed.
  set(LIBCXX_CONFIGURED_INCLUDE_DIRS "${LIBCXX_GENERATED_INCLUDE_DIR}")
else()
  # It's important that the arch directory be included first so that its header files
  # which interpose on the default include dir be included instead of the default ones.
  set(LIBCXX_CONFIGURED_INCLUDE_DIRS
    "${LIBCXX_GENERATED_INCLUDE_TARGET_DIR};${LIBCXX_GENERATED_INCLUDE_DIR}"
  )
endif()
configure_file(
  "CMakeLists.txt.in"
  "${LIBCXX_GENERATED_MODULE_DIR}/CMakeLists.txt"
  @ONLY
)

set(LIBCXX_MODULE_STD_INCLUDE_SOURCES)
foreach(file ${LIBCXX_MODULE_STD_SOURCES})
  set(
    LIBCXX_MODULE_STD_INCLUDE_SOURCES
    "${LIBCXX_MODULE_STD_INCLUDE_SOURCES}#include \"${file}\"\n"
  )
endforeach()

configure_file(
  "std.cppm.in"
  "${LIBCXX_GENERATED_MODULE_DIR}/std.cppm"
  @ONLY
)

set(_all_modules)
list(APPEND _all_modules "${LIBCXX_GENERATED_MODULE_DIR}/CMakeLists.txt")
list(APPEND _all_modules "${LIBCXX_GENERATED_MODULE_DIR}/std.cppm")
foreach(file ${LIBCXX_MODULE_STD_SOURCES})
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
  set(src "${CMAKE_CURRENT_SOURCE_DIR}/${file}")
  set(dst "${LIBCXX_GENERATED_MODULE_DIR}/${file}")
  add_custom_command(OUTPUT ${dst}
    DEPENDS ${src}
    COMMAND ${CMAKE_COMMAND} -E copy_if_different ${src} ${dst}
    COMMENT "Copying CXX module ${file}")
  list(APPEND _all_modules "${dst}")
endforeach()

add_custom_target(generate-cxx-modules
  ALL DEPENDS
    ${_all_modules}
)

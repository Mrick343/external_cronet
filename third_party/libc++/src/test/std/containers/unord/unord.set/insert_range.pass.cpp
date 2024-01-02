//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// <unordered_set>

// template <class Value, class Hash = hash<Value>, class Pred = equal_to<Value>,
//           class Alloc = allocator<Value>>
// class unordered_set

// template <class InputIterator>
//     void insert(InputIterator first, InputIterator last);

#include <unordered_set>
#include <cassert>

#include "test_macros.h"
#include "test_iterators.h"
#include "min_allocator.h"

int main(int, char**)
{
    {
        typedef std::unordered_set<int> C;
        typedef int P;
        P a[] =
        {
            P(1),
            P(2),
            P(3),
            P(4),
            P(1),
            P(2)
        };
        C c;
        c.insert(cpp17_input_iterator<P*>(a), cpp17_input_iterator<P*>(a + sizeof(a)/sizeof(a[0])));
        assert(c.size() == 4);
        assert(c.count(1) == 1);
        assert(c.count(2) == 1);
        assert(c.count(3) == 1);
        assert(c.count(4) == 1);
    }
#if TEST_STD_VER >= 11
    {
        typedef std::unordered_set<int, std::hash<int>,
                                      std::equal_to<int>, min_allocator<int>> C;
        typedef int P;
        P a[] =
        {
            P(1),
            P(2),
            P(3),
            P(4),
            P(1),
            P(2)
        };
        C c;
        c.insert(cpp17_input_iterator<P*>(a), cpp17_input_iterator<P*>(a + sizeof(a)/sizeof(a[0])));
        assert(c.size() == 4);
        assert(c.count(1) == 1);
        assert(c.count(2) == 1);
        assert(c.count(3) == 1);
        assert(c.count(4) == 1);
    }
#endif
=======
// UNSUPPORTED: c++03, c++11, c++14, c++17, c++20
// Some fields in the test case variables are deliberately not explicitly initialized, this silences a warning on GCC.
// ADDITIONAL_COMPILE_FLAGS: -Wno-missing-field-initializers

// <set>

// template<container-compatible-range<value_type> R>
//   void insert_range(R&& rg); // C++23

#include <unordered_set>

#include "../../insert_range_maps_sets.h"
#include "test_macros.h"

int main(int, char**) {
  for_all_iterators_and_allocators<int, const int*>([]<class Iter, class Sent, class Alloc>() {
    test_map_set_insert_range<std::unordered_set<int, test_hash<int>, test_equal_to<int>, Alloc>, int, Iter, Sent>();
  });

  static_assert(test_set_constraints_insert_range<std::unordered_set, int, double>());

  test_set_insert_range_move_only<std::unordered_set>();

  test_set_insert_range_exception_safety_throwing_copy<std::unordered_set>();
  test_unord_set_insert_range_exception_safety_throwing_allocator<std::unordered_set, int>();
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

  return 0;
}

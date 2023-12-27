//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
// <unordered_map>

// template <class Key, class T, class Hash = hash<Key>, class Pred = equal_to<Key>,
//           class Alloc = allocator<pair<const Key, T>>>
// class unordered_multimap

// template <class InputIterator>
//     void insert(InputIterator first, InputIterator last);

#include <unordered_map>
#include <string>
#include <set>
#include <cassert>
#include <cstddef>

#include "test_macros.h"
#include "../../../check_consecutive.h"
#include "test_iterators.h"
#include "min_allocator.h"

int main(int, char**)
{
    {
        typedef std::unordered_multimap<int, std::string> C;
        typedef std::pair<int, std::string> P;
        P a[] =
        {
            P(1, "one"),
            P(2, "two"),
            P(3, "three"),
            P(4, "four"),
            P(1, "four"),
            P(2, "four"),
        };
        C c;
        c.insert(cpp17_input_iterator<P*>(a), cpp17_input_iterator<P*>(a + sizeof(a)/sizeof(a[0])));
        assert(c.size() == 6);
        typedef std::pair<C::iterator, C::iterator> Eq;
        Eq eq = c.equal_range(1);
        assert(std::distance(eq.first, eq.second) == 2);
        std::multiset<std::string> s;
        s.insert("one");
        s.insert("four");
        CheckConsecutiveKeys<C::const_iterator>(c.find(1), c.end(), 1, s);
        eq = c.equal_range(2);
        assert(std::distance(eq.first, eq.second) == 2);
        s.insert("two");
        s.insert("four");
        CheckConsecutiveKeys<C::const_iterator>(c.find(2), c.end(), 2, s);
        eq = c.equal_range(3);
        assert(std::distance(eq.first, eq.second) == 1);
        C::iterator k = eq.first;
        assert(k->first == 3);
        assert(k->second == "three");
        eq = c.equal_range(4);
        assert(std::distance(eq.first, eq.second) == 1);
        k = eq.first;
        assert(k->first == 4);
        assert(k->second == "four");
        assert(static_cast<std::size_t>(std::distance(c.begin(), c.end())) == c.size());
        assert(static_cast<std::size_t>(std::distance(c.cbegin(), c.cend())) == c.size());
    }
#if TEST_STD_VER >= 11
    {
        typedef std::unordered_multimap<int, std::string, std::hash<int>, std::equal_to<int>,
                            min_allocator<std::pair<const int, std::string>>> C;
        typedef std::pair<int, std::string> P;
        P a[] =
        {
            P(1, "one"),
            P(2, "two"),
            P(3, "three"),
            P(4, "four"),
            P(1, "four"),
            P(2, "four"),
        };
        C c;
        c.insert(cpp17_input_iterator<P*>(a), cpp17_input_iterator<P*>(a + sizeof(a)/sizeof(a[0])));
        assert(c.size() == 6);
        typedef std::pair<C::iterator, C::iterator> Eq;
        Eq eq = c.equal_range(1);
        assert(std::distance(eq.first, eq.second) == 2);
        std::multiset<std::string> s;
        s.insert("one");
        s.insert("four");
        CheckConsecutiveKeys<C::const_iterator>(c.find(1), c.end(), 1, s);
        eq = c.equal_range(2);
        assert(std::distance(eq.first, eq.second) == 2);
        s.insert("two");
        s.insert("four");
        CheckConsecutiveKeys<C::const_iterator>(c.find(2), c.end(), 2, s);
        eq = c.equal_range(3);
        assert(std::distance(eq.first, eq.second) == 1);
        C::iterator k = eq.first;
        assert(k->first == 3);
        assert(k->second == "three");
        eq = c.equal_range(4);
        assert(std::distance(eq.first, eq.second) == 1);
        k = eq.first;
        assert(k->first == 4);
        assert(k->second == "four");
        assert(static_cast<std::size_t>(std::distance(c.begin(), c.end())) == c.size());
        assert(static_cast<std::size_t>(std::distance(c.cbegin(), c.cend())) == c.size());
    }
#endif

  return 0;
}
=======
// UNSUPPORTED: c++03, c++11, c++14, c++17, c++20
// Some fields in the test case variables are deliberately not explicitly initialized, this silences a warning on GCC.
// ADDITIONAL_COMPILE_FLAGS: -Wno-missing-field-initializers

// <unordered_map>

// template<container-compatible-range<value_type> R>
//   void insert_range(R&& rg); // C++23

#include <unordered_map>

#include "../../../insert_range_maps_sets.h"
#include "test_macros.h"

int main(int, char**) {
  // Note: we want to use a pair with non-const elements for input (an assignable type is a lot more convenient) but
  // have to use the exact `value_type` of the map (that is, `pair<const K, V>`) for the allocator.
  using Pair = std::pair<int, char>;
  using ConstPair = std::pair<const int, char>;
  for_all_iterators_and_allocators<ConstPair, const Pair*>([]<class Iter, class Sent, class Alloc>() {
    test_map_set_insert_range<std::unordered_multimap<int, char, test_hash<int>, test_equal_to<int>, Alloc>, Pair, Iter, Sent>(/*allow_duplicates=*/true);
  });

  static_assert(test_map_constraints_insert_range<std::unordered_multimap, int, int, char, double>());

  test_map_insert_range_move_only<std::unordered_multimap>();

  test_map_insert_range_exception_safety_throwing_copy<std::unordered_multimap>();
  test_unord_map_insert_range_exception_safety_throwing_allocator<std::unordered_multimap, int, int>();

  return 0;
}

>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)

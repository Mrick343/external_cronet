//===----------------------------------------------------------------------===//
//
// Part of the LLVM Project, under the Apache License v2.0 with LLVM Exceptions.
// See https://llvm.org/LICENSE.txt for license information.
// SPDX-License-Identifier: Apache-2.0 WITH LLVM-exception
//
//===----------------------------------------------------------------------===//

#include <algorithm>

#include "common.h"

namespace {
template <class ValueType, class Order>
struct StableSort {
  size_t Quantity;

  void run(benchmark::State& state) const {
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
    runOpOnCopies<ValueType>(state, Quantity, Order(), BatchSize::CountElements, [](auto& Copy) {
=======
    runOpOnCopies<ValueType>(state, Quantity, Order(), BatchSize::CountBatch, [](auto& Copy) {
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)
      std::stable_sort(Copy.begin(), Copy.end());
    });
  }

  bool skip() const { return Order() == ::Order::Heap; }

  std::string name() const {
    return "BM_StableSort" + ValueType::name() + Order::name() + "_" + std::to_string(Quantity);
  };
};
} // namespace

int main(int argc, char** argv) {
  benchmark::Initialize(&argc, argv);
  if (benchmark::ReportUnrecognizedArguments(argc, argv))
    return 1;
  makeCartesianProductBenchmark<StableSort, AllValueTypes, AllOrders>(Quantities);
  benchmark::RunSpecifiedBenchmarks();
}

set(LLVM_USE_SANITIZER "Address" CACHE STRING "")
<<<<<<< HEAD   (d5875e Merge remote-tracking branch 'aosp/main' into upstream_stagi)
# This is a temporary (hopefully) workaround for an ASan issue (see https://llvm.org/D119410).
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -mllvm -asan-use-private-alias=1" CACHE INTERNAL "")
=======
>>>>>>> BRANCH (424e1f Import Cronet version 121.0.6103.2)

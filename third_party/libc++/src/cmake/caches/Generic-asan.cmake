set(LLVM_USE_SANITIZER "Address" CACHE STRING "")
<<<<<<< HEAD   (ddd8f6 Merge remote-tracking branch 'aosp/main' into upstream_stagi)
# This is a temporary (hopefully) workaround for an ASan issue (see https://llvm.org/D119410).
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -mllvm -asan-use-private-alias=1" CACHE INTERNAL "")
=======
>>>>>>> BRANCH (a593a1 Import Cronet version 121.0.6103.2)

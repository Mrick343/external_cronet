set(LLVM_USE_SANITIZER "Address" CACHE STRING "")
<<<<<<< HEAD   (1e5f44 Merge changes I2f93b488,I33a20e84 into upstream-staging)
# This is a temporary (hopefully) workaround for an ASan issue (see https://llvm.org/D119410).
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -mllvm -asan-use-private-alias=1" CACHE INTERNAL "")
=======
>>>>>>> BRANCH (1552c4 Import Cronet version 121.0.6103.2)

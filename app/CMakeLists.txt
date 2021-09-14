
cmake_minimum_required(VERSION 3.4.1)

#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=gnu++11")
#判断编译器类型,如果是gcc编译器,则在编译选项中加入c++11支持
if(CMAKE_COMPILER_IS_GNUCXX)
    set(CMAKE_CXX_FLAGS "-std=c++11 ${CMAKE_CXX_FLAGS}")
    message(STATUS "optional:-std=c++11")
endif(CMAKE_COMPILER_IS_GNUCXX)

include_directories(src/main/jniLibs/include)


set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS}  -L${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}" )

add_library( # Sets the name of the library.
             native-lib

             SHARED

             src/main/cpp/native-lib.cpp src/main/cpp/BitmapMatUtils.cpp
             src/main/cpp/cardocr.cpp)


find_library(
              log-lib
              log )


target_link_libraries(
                       native-lib opencv_java4

                       #加入该依赖库
                       jnigraphics
                       ${log-lib} )
file(GLOB_RECURSE sources CONFIGURE_DEPENDS
    src/*.c
    src/*.h
    include/*.h
)

add_executable(image-transformer ${sources}
        include/bmp.h
        include/pixel.h
        include/image.h
        include/image_io.h
        include/rotate.h
        src/image.c
        src/image_io.c
        src/rotate.c)
target_include_directories(image-transformer PRIVATE src include)

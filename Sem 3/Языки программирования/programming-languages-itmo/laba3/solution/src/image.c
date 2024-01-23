//
// Created by Михаил Билошицкий on 10.01.2024.
//

#include "image.h"
#include "pixel.h"
#include <stdio.h>
#include <stdlib.h>

struct image create_image(uint64_t width, uint64_t height, size_t pixel_size) {
    struct image img = { .width = width, .height = height };
    img.data = malloc(pixel_size * width * height);
    if (img.data == NULL) {
        fprintf(stderr, "Can't allocate memory for image.");
        exit(1);
    }
    return img;
}

//
// Created by Михаил Билошицкий on 10.01.2024.
//

#ifndef IMAGE_TRANSFORMER_IMAGE_H
#define IMAGE_TRANSFORMER_IMAGE_H

#include <stdint.h>
#include <stdio.h>

struct image {
    uint64_t width, height;
    struct pixel* data;
};

struct image create_image(uint64_t width, uint64_t height, size_t pixel_size);

#endif //IMAGE_TRANSFORMER_IMAGE_H

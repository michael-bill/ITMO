//
// Created by Михаил Билошицкий on 10.01.2024.
//

#ifndef IMAGE_TRANSFORMER_PIXEL_H
#define IMAGE_TRANSFORMER_PIXEL_H

#include <stdint.h>

#pragma pack(push, 1)
struct pixel {
    uint8_t b, g, r;
};
#pragma pack(pop)

#endif //IMAGE_TRANSFORMER_PIXEL_H

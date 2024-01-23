//
// Created by Михаил Билошицкий on 10.01.2024.
//

#ifndef IMAGE_TRANSFORMER_ROTATE_H
#define IMAGE_TRANSFORMER_ROTATE_H

#include "image.h"
#include <stdbool.h>

// Checks if the angle for rotation is valid
bool is_rotation_valid(int32_t angle);

// Creates a copy of the image that is rotated 90 degrees
struct image rotate(struct image const source);

#endif //IMAGE_TRANSFORMER_ROTATE_H

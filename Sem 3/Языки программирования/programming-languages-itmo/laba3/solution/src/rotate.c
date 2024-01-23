//
// Created by Михаил Билошицкий on 10.01.2024.
//

#include "rotate.h"
#include "image.h"
#include "pixel.h"
#include <stdlib.h>

bool is_rotation_valid(int32_t angle) {
    return angle % 90 == 0;
}

struct image rotate(struct image const source) {
    struct image rotated = create_image(source.height, source.width, sizeof(struct pixel));
    for (uint64_t i = 0; i < rotated.height; i++) {
        for (uint64_t j = 0; j < rotated.width; j++) {
            rotated.data[rotated.width * i + j] = source.data[source.width * (j + 1) - i - 1];
        }
    }
    free(source.data);
    return rotated;
}

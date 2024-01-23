#include "image.h"
#include "image_io.h"
#include "rotate.h"
#include <stdlib.h>
#include <string.h>

void exit_with_error(const char* message, const char* arg) {
    fprintf(stderr, message, arg);
    exit(1);
}

int main(int argc, char** argv) {
    if (argc == 2 && strcmp(argv[1], "-help") == 0) {
        fprintf(stdout, "Usage: %s <source-image> <transformed-image> <angle>\n"
                       "Angle must be: 0, 90, -90, 180, -180, 270, -270", argv[0]);
        return 0;
    }

    if (argc != 4) exit_with_error("Arguments are not enough. Write -help to view information.", "");

    const char* source_image_path = argv[1];
    const char* transformed_image_path = argv[2];
    const int32_t angle = atoi(argv[3]);

    if (!is_rotation_valid(angle)) exit_with_error("Angle must be: 0, 90, -90, 180, -180, 270, -270", "");

    FILE* source_file = fopen(source_image_path, "rb");
    if (source_file == NULL) exit_with_error("Can't open source image: %s", source_image_path);

    struct image source_image;
    enum read_status status = from_bmp(source_file, &source_image);
    fclose(source_file);

    if (status != READ_OK) exit_with_error("Can't read source image: %s", source_image_path);

    int32_t rotations_count = ((angle + 360) % 360) / 90;
    struct image transformed_image = source_image;
    for (int32_t i = 0; i < rotations_count; i++) {
        transformed_image = rotate(transformed_image);
    }

    FILE* transformed_image_file = fopen(transformed_image_path, "wb");
    if (transformed_image_file == NULL) exit_with_error("Can't write transformed image: %s", transformed_image_path);

    enum write_status write_status = to_bmp(transformed_image_file, &transformed_image);
    fclose(transformed_image_file);
    free(transformed_image.data);

    if (write_status != WRITE_OK) exit_with_error("Can't write transformed image: %s", transformed_image_path);

    return 0;
}

//
// Created by Михаил Билошицкий on 10.01.2024.
//

#include "image_io.h"
#include "bmp.h"
#include "pixel.h"

#include <stdio.h>
#include <stdlib.h>

#define BMP_WIDTH_MULTIPLE 4
#define BYTE_SIZE 8

#define BMP_TYPE 0x4D42
#define BMP_RESERVED 0
#define BMP_BI_SIZE 40
#define BMP_BI_PLANES 1
#define BMP_BI_COMPRESS 0
#define BMP_BI_PERLS_PER_METER 0
#define BMP_BI_CLR_USED 0
#define BMP_BI_CLR_IMPORTANT 0


enum read_status from_bmp(FILE* in, struct image* image) {
    struct bmp_header header;
    if (fread(&header, sizeof(struct bmp_header), 1, in) != 1)
        return READ_INVALID_HEADER;
    if (header.bfType != BMP_TYPE)
        return READ_INVALID_SIGNATURE;
    size_t pixel_size = header.biBitCount / BYTE_SIZE;
    *image = create_image(header.biWidth, header.biHeight, pixel_size);
    for (uint32_t i = 0; i < header.biHeight; i++) {
        size_t bytes_read = fread(image->data + i * header.biWidth, pixel_size, header.biWidth, in);
        if (bytes_read != header.biWidth) {
            free(image->data);
            return READ_INVALID_BITS;
        }
        long skip_step = BMP_WIDTH_MULTIPLE - ((header.biWidth * pixel_size) % BMP_WIDTH_MULTIPLE);
        fseek(in, skip_step, SEEK_CUR);
    }
    return READ_OK;
}


enum write_status to_bmp(FILE *out, const struct image *image) {
    struct bmp_header header = {
        .bfType = BMP_TYPE,
        .bfileSize = sizeof(struct bmp_header) + sizeof(struct pixel) * image->width * image->height,
        .bfReserved = BMP_RESERVED,
        .bOffBits = sizeof(struct bmp_header),
        .biSize = BMP_BI_SIZE,
        .biWidth = image->width,
        .biHeight = image->height,
        .biPlanes = BMP_BI_PLANES,
        .biBitCount = sizeof(struct pixel) * BYTE_SIZE,
        .biCompression = BMP_BI_COMPRESS,
        .biSizeImage = sizeof(struct pixel) * image->width * image->height,
        .biXPelsPerMeter = BMP_BI_PERLS_PER_METER,
        .biYPelsPerMeter = BMP_BI_PERLS_PER_METER,
        .biClrUsed = BMP_BI_CLR_USED,
        .biClrImportant = BMP_BI_CLR_IMPORTANT
    };
    if (fwrite(&header, sizeof(struct bmp_header), 1, out) != 1)
        return WRITE_ERROR;
    size_t pixel_size = header.biBitCount / BYTE_SIZE;
    for (uint32_t i = 0; i < header.biHeight; i++) {
        size_t bytes_write = fwrite(&image->data[i * header.biWidth], pixel_size, header.biWidth, out);
        if (bytes_write != header.biWidth) {
            return WRITE_ERROR;
        }
        size_t skip_step = BMP_WIDTH_MULTIPLE - ((header.biWidth * pixel_size) % BMP_WIDTH_MULTIPLE);
        char trash_buffer[skip_step];
        for (size_t j = 0; j < skip_step; j++) trash_buffer[j] = 0;
        fwrite(trash_buffer, sizeof(char), skip_step, out);
    }
    return WRITE_OK;
}

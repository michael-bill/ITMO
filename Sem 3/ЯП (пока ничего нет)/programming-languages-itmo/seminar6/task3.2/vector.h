#include <inttypes.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>


struct vector;

struct vector* vector_create(size_t);

void vector_destroy(struct vector *a);

void vector_push_int64(struct vector* a, int64_t value);

void vector_push_vector(struct vector* a, struct vector* b);

int64_t* vector_get_at(struct vector* a, size_t i);

bool vector_set_at(struct vector* a, size_t i, int64_t value);

size_t vector_count(struct vector const* a);

size_t vector_capacity(struct vector const* a);

void vector_change_size(struct vector* a, size_t i);

void vector_print(struct vector* a);

void vector_print_element(struct vector* a, size_t i);
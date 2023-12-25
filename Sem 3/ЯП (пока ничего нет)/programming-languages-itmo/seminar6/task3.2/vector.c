#include "vector.h"

struct vector {
    size_t count;
    size_t capacity;
    int64_t *data;
};

struct vector* vector_create(size_t count) {
    struct vector* a = malloc(sizeof(struct vector));
    a->data = malloc(sizeof(int64_t) * count);
    if (a->data == NULL) {
        free(a);
        return NULL;
    }
    a->count = 0;
    a->capacity = count;
    return a;
}

void vector_destroy(struct vector *a) {
    free( a-> data );
    free( a );
}

void vector_push_int64(struct vector* a, int64_t value) {
    if (a->count == a->capacity) {
        vector_change_size(a, a->capacity * 2);
    }
    a->data[a->count++] = value;
}

void vector_push_vector(struct vector* a, struct vector* b) {
    for (size_t i = 0; i < b->count; i++) {
        vector_push_int64(a, b->data[i]);
    }
}

int64_t* vector_get_at(struct vector* a, size_t i) {
    if (i >= a->count) { return NULL; }
    return a->data + i;
}

bool vector_set_at(struct vector* a, size_t i, int64_t value) {
    if (i >= a->count) return false;
    a->data[i] = value;
    return true;
}

size_t vector_count(struct vector const* a) {
    return a->count;
}

size_t vector_capacity(struct vector const* a) {
    return a->capacity;
}

void vector_change_size(struct vector* a, size_t i) {
    a->data = realloc(a->data, sizeof(int64_t) * i);
    if (i < a->count)
        a->count = i;
    a->capacity = i;
}

static void print_value(int64_t value) {
    printf("%" PRId64 " ", value);
}

void vector_print(struct vector* a) {
    for (size_t i = 0; i < a->count; ++i) {
        print_value(a->data[i]);
    }
}

void vector_print_element(struct vector* a, size_t i) {
    if (i >= a->count) { return; }
    print_value(a->data[i]);
}
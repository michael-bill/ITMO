#include "vector.h"

int main( void ) {

    struct vector* a = vector_create(10);
    struct vector* b = vector_create(10);

    if (a == NULL || b == NULL) {
        printf("Failed to allocate memory\n");
        return 1;
    }

    vector_push_int64(a, 1);
    vector_push_int64(a, 2);
    vector_push_int64(a, 3);
    vector_push_int64(b, 555);

    // Print vectors 
    vector_print(a);
    printf("\n");
    vector_print(b);
    printf("\n");
    printf("\n");

    vector_push_vector(b, a);

    // Print vectors 
    vector_print(a);
    printf("\n");
    vector_print(b);
    printf("\n");

    vector_change_size(a, 2);
    vector_print(a);
    printf("\n");

    vector_destroy(a);
    vector_destroy(b);
    return 0;
}
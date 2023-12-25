#include <stdio.h>
#include <stdlib.h>

#define DEFINE_LIST(type)                                                   \
    struct list_##type {                                                    \
        type value;                                                         \
        struct list_##type* next;                                           \
    };                                                                      \
    void list_##type##_push(struct list_##type** head, type value) {        \
        struct list_##type* new = malloc(sizeof(struct list_##type));       \
        new->value = value;                                                 \
        new->next = *head;                                                  \
        *head = new;                                                        \
    }                                                                       \
    void list_##type##_print(struct list_##type* head) {                    \
        while (head) {                                                      \
            _Generic(head->value,                                           \
                double : printf("%lf ", head->value),                       \
                float  : printf("%f ", head->value),                        \
                default : printf("%d ", head->value));                      \
            head = head->next;                                              \
        }                                                                   \
        printf("\n");                                                       \
    }


DEFINE_LIST(int64_t)
DEFINE_LIST(double)

int main(int argc, char *argv[]) {
    struct list_int64_t* head = malloc(sizeof(struct list_int64_t));
    head->value = 0;
    head->next = NULL;
    list_int64_t_push(&head, 1);
    list_int64_t_push(&head, 2);
    list_int64_t_push(&head, 3);
    list_int64_t_print(head);

    struct list_double* headd = malloc(sizeof(struct list_double));
    headd->value = 7;
    headd->next = NULL;
    list_double_push(&headd, 1.123123);
    list_double_push(&headd, 2.83796);
    list_double_push(&headd, 3.19248);
    list_double_print(headd);

    return 0;
}
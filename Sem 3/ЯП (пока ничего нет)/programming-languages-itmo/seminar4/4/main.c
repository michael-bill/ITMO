#include <stdio.h>
#include <stdlib.h>

extern void print_file();

int main() {
    char fullName[4096];
    printf("Type filename: \n");
    if (fgets(fullName, sizeof(fullName), stdin)) {
        print_file(fullName);
    }
    return 0;
}
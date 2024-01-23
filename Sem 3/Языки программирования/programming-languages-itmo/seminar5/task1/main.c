#include <stdio.h>
#define print_var(x) printf(#x " is %d\n", x )

#define PI 3.14
#define STR(x) #x
int a = 10;
const b = 20;

int main(int argc, char *argv[])
{
    print_var(PI);
    print_var(STR("asd"));
    print_var(a);
    print_var(b);
    return 0;
}
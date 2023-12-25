extern void print_string(char* string);
extern int string_length(char* string);

int main(int argc, char** argv) {
    char* string = "Hello, World!\n\0";
    print_string(string);
}
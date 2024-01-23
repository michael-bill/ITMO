%include "colon.inc"
%include "dict.inc"
%include "lib.inc"
%include "words.inc"

%define BUFFER_SIZE 255

global _start
section .rodata
input_error: db "Provided key is invalid", 0
find_error: db "Provided element is not found", 0

section .bss
buffer: resb BUFFER_SIZE
section .text
_start:
    mov rdi, buffer
    mov rsi, BUFFER_SIZE
    call read_word
    test rax, rax
    jz .input_error
    mov rdi, buffer
    mov rsi, connection
    call find_word
    test rax, rax
    jz .find_error
    mov rdi, rax
    add rdi, 8
    call string_length
    add rdi, rax
    inc rdi
    call print_string
    jmp exit
    .input_error:
        mov rdi, input_error
        call print_error
        jmp exit
    .find_error:
        mov rdi, find_error
        call print_error
        jmp exit
%define SYS_WRITE 1
%define FD_STDOUT 1

global print_string
global string_length

section .text

string_length:
    xor rax, rax
    .loop
        cmp byte [rdi + rax], 0
        je .exit
        inc rax
        jmp .loop
    .exit
        ret

print_string:
    mov rsi, rdi
    call string_length
    mov rdx, rax
    mov rax, SYS_WRITE
    mov rdi, FD_STDOUT
    syscall
    ret

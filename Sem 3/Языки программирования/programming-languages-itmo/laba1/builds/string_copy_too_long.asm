
%macro call 1
mov rax, -1
push rbx
push rbp
push r12 
push r13 
push r14 
push r15 
call %1
cmp r15, [rsp] 
jne convention_error
pop r15
cmp r14, [rsp] 
jne convention_error
pop r14
cmp r13, [rsp] 
jne convention_error
pop r13
cmp r12, [rsp] 
jne convention_error
pop r12
cmp rbp, [rsp] 
jne convention_error
pop rbp
cmp rbx, [rsp] 
jne convention_error
pop rbx
mov rdi, -1
mov rsi, -1
mov rcx, -1
mov r8, -1
mov r9, -1
mov r10, -1
mov r11, -1
%endmacro

%include "lib.inc"

global _start

section .text
convention_error:
    mov rax, 1
    mov rdi, 2
    mov rsi, err_calling_convention
    mov rdx, err_calling_convention.end - err_calling_convention
    syscall
    mov rax, 60
    mov rdi, -41
    syscall

section .data
    err_calling_convention: db "You did not respect the calling convention! Check that you handled caller-saved and callee-saved registers correctly", 10
    .end:

section .rodata
err_too_long_msg: db "string is too long", 10, 0

section .data
arg1: db '', 0
arg2: times 0 db  66

section .text
_start:
    mov rdi, arg1
    mov rsi, arg2
    mov rdx, 0
    call string_copy
    test rax, rax
    jnz .good
    mov rdi, err_too_long_msg 
    call print_string
    jmp _exit
    .good:
    mov rdi, arg2 
    call print_string
_exit:
    mov rax, 60
    xor rdi, rdi
    syscall

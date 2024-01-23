%include "lib.inc"
global find_word
section .text
; rdi - указатель на строку
; rsi - указатель на начало массива
find_word:
    push r12
    push r13
    mov r12, rdi
    mov r13, rsi
    .loop:
        test rsi, rsi
        jz .fail
        add rsi, 8
        mov rdi, r12
        call string_equals
        test rax, rax
        jz .next
        mov rax, r13
        jmp .exit
    .fail:
        xor rax, rax
        jmp .exit
    .next:
        mov rsi, [r13]
        mov r13, rsi
        jmp .loop
    .exit:
        pop r13
        pop r12
        ret
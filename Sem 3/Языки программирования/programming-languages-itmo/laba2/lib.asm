%macro parse_uint_with_sign 1
    inc rdi
    call parse_uint
    test rdx, rdx
    jz %1
    inc rdx
%endmacro
%macro read_word_check_end 2
    test al, al
    jz %1
    cmp r13, r14
    je %2
%endmacro

global exit
global string_length
global print_error
global print_string
global print_newline
global print_char
global print_int
global print_uint
global string_equals
global read_char
global read_word
global parse_uint
global parse_int
global string_copy 

; Принимает код возврата и завершает текущий процесс
exit: 
    mov rax, 60 ; Exit code
    syscall

; Принимает указатель на нуль-терминированную строку, возвращает её длину
string_length:
    xor rax, rax
    .loop:
        cmp byte [rdi+rax], 0
        je .done
        inc rax
        jmp .loop
    .done:
        ret

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    mov rsi, 1
    .print
        push rsi
        push rdi
        call string_length
        pop rsi ; Address
        pop rdi ; std___
        mov rdx, rax ; Length
        mov rax, 1 ; write
        syscall
        ret
print_error:
    mov rsi, 2
    jmp print_string.print


; Переводит строку (выводит символ с кодом 0xA)
print_newline:
    mov rdi, `\n`
; Принимает код символа и выводит его в stdout
print_char:
    push rdi
    mov rdx, 1 ; Length
    mov rdi, 1 ; stdout
    mov rax, 1 ; write
    mov rsi, rsp ; Char
    syscall
    pop rdi
    ret

; Выводит знаковое 8-байтовое число в десятичном формате 
print_int:
    test rdi, rdi
    jns print_uint
    push rdi
    mov dil, '-'
    call print_char
    pop rdi
    neg rdi
; Выводит беззнаковое 8-байтовое число в десятичном формате 
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.
print_uint:
    cmp rdi, 0xFFFFFFFFFFFFFFFF   ; Сравнение входного числа с максимальным значением
    jbe .continue
    ret
.continue:
    xor rax, rax
    mov rbp, rsp
    dec rbp
    mov byte [rbp], 0
    mov rax, rdi
    mov rdi, 10
    .loop:
        xor rdx, rdx
        div rdi
        add rdx, '0'
        dec rbp
        mov byte [rbp], dl
        test rax, rax
        jnz .loop
    mov rdi, rbp
    push rsi
    call print_string
    pop rsi
    ret

; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе
string_equals:
    .loop:
        mov al, byte [rdi]
        cmp al, [rsi]
        jne .fail
        cmp al, 0
        je .success
        inc rdi
        inc rsi
        jmp .loop
    .fail:
        xor rax, rax
        ret
    .success:
        mov rax, 1
        ret

; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока
read_char:
    push 0
    mov rax, 0
    mov rdi, 0
    mov rsi, rsp
    mov rdx, 1
    syscall
    test rax, rax
    jz .exit
    xor rax, rax
    mov al, [rsp]
    .exit:
        pop rdi
        ret

; Принимает: адрес начала буфера (rdi), размер буфера (rsi)
; Читает в буфер слово из stdin, пропуская пробельные символы в начале, .
; Пробельные символы это пробел 0x20, табуляция 0x9 и перевод строки 0xA.
; Останавливается и возвращает 0 если слово слишком большое для буфера
; При успехе возвращает адрес буфера в rax, длину слова в rdx.
; При неудаче возвращает 0 в rax
; Эта функция должна дописывать к слову нуль-терминатор

read_word:
    push r12
    push r13
    push r14
    mov r12, rdi ; rdi
    mov r13, 0 ; rbx
    mov r14, rsi ; rsi
    xor r13, r13
    .starter_skip:
        call read_char
        cmp al, ' '
        je .starter_skip
        cmp al, `\t`
        je .starter_skip
        cmp al, `\n`
        je .starter_skip
    read_word_check_end .finish, .abort
    .loop:
        cmp al, ' '
        je .finish
        cmp al, `\t`
        je .finish
        cmp al, `\n`
        je .finish
        mov [r12+r13], al
        inc r13
        call read_char
        read_word_check_end .finish, .abort
        jmp .loop
    .abort:
        xor rax, rax
        jmp .exit
    .finish:
        mov rax, r12
        mov rdx, r13
        mov byte [rax + r13], 0
    .exit:
        pop r14
        pop r13
        pop r12
        ret

; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
parse_uint:
    xor rax, rax
    xor rdx, rdx
    .loop:
        movzx rcx, byte [rdi]
        test rcx, rcx
        jz .finish
        cmp rcx, '0'
        jl .finish
        cmp rcx, '9'
        jg .finish
        sub rcx, '0'
        imul rax, rax, 10
        add rax, rcx
        inc rdx
        inc rdi
        jmp .loop
    .finish:
        ret




; Принимает указатель на строку, пытается
; прочитать из её начала знаковое число.
; Если есть знак, пробелы между ним и числом не разрешены.
; Возвращает в rax: число, rdx : его длину в символах (включая знак, если он был) 
; rdx = 0 если число прочитать не удалось
parse_int:
    xor rax, rax
    xor rdx, rdx
    movzx rcx, byte[rdi]
    test rcx, rcx
    jz .finish
    cmp rcx, '-'
    jz .negative
    cmp rcx, '+'
    jz .positive
    jmp parse_uint
    .positive:
        parse_uint_with_sign .finish
        jmp .finish
    .negative:
        parse_uint_with_sign .finish
        neg rax
    .finish:
        ret

; Принимает указатель на строку (rdi), указатель на буфер (rsi) и длину буфера (rdx)
; Копирует строку в буфер
; Возвращает длину строки если она умещается в буфер, иначе 0
string_copy:
    xor rax, rax
    xor rcx, rcx
    test rdx, rdx
    jz .finish
    .loop:
        mov al, [rdi + rcx]
        mov [rsi + rcx], al
        test al, al
        jz .finish
        inc rcx
        cmp rcx, rdx
        jae .fail
        jmp .loop
    .fail:
        xor rax, rax
        ret
    .finish:
        mov rax, rcx
        ret
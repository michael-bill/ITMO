%define EXIT_CODE 60
%define OUTPUT_CODE 1

; Принимает код возврата и завершает текущий процесс
exit: 
    mov rax, EXIT_CODE
    syscall
    
; Принимает указатель на нуль-терминированную строку, возвращает её длину
string_length:
    xor rax, rax
    .loop:
        cmp byte [rdi + rax], 0
        je .exit_loop
        inc rax
        jmp .loop
    .exit_loop:
        ret

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    push rdi
    call string_length
    pop rsi
    mov rdx, rax
    mov rax, OUTPUT_CODE
    mov rdi, OUTPUT_CODE
    syscall
    ret

; Принимает код символа и выводит его в stdout
print_char:
    push rdi
    mov rsi, rsp
    mov rdx, 1
    mov rax, OUTPUT_CODE
    mov rdi, OUTPUT_CODE
    syscall
    pop rdi
    ret

; Переводит строку (выводит символ с кодом 0xA)
print_newline:
    mov rdi, '\n'
    call print_char
    ret

; Выводит беззнаковое 8-байтовое число в десятичном формате 
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.
print_uint:
    push rbp
    mov rbp, rsp
    sub rsp, 16
    mov rsi, 1
    mov byte [rsp+8], 0
    mov rax, rdi
    .loop:
        mov rdi, 10
        xor rdx, rdx
        div rdi
        add dl, '0'
        dec rsp
        mov byte [rsp], dl
        inc rsi
        test rax, rax
        jnz .loop
    mov rdi, rsp
    mov rsi, 8
    push rsi
    call print_string
    add rsp, 8
    mov rsp, rbp
    pop rbp
    ret


; Выводит знаковое 8-байтовое число в десятичном формате 
print_int:
    test rdi, rdi
    jns print_uint
    push rdi
    mov rdi, '-'
    call print_char
    pop rdi
    neg rdi
    jmp print_uint

; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе
string_equals:
    push rbx
    xor rax, rax
    xor rbx, rbx
    .loop:
        mov al, byte [rdi]
        mov bl, byte [rsi]
        cmp al, bl
        jne .notequal
        test al, al
        jz .equal
        inc rdi
        inc rsi
        jmp .loop
    .notequal:
        pop rbx
        xor rax, rax
        ret
    .equal:
        pop rbx
        mov rax, 1
        ret

; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока
read_char:
    push 0
    xor rax, rax
    xor rdi, rdi
    mov rsi, rsp
    mov rdx, 1
    syscall
    test rax, rax
    js .exit
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
    push rbx
    mov r8, rdi
    mov r9, rsi
    push rdi
    push rsi
    .starter_skip:
        call read_char
        cmp al, ' '
        je .starter_skip
        cmp al, `\t`
        je .starter_skip
        cmp al, `\n`
        je .starter_skip
    pop rsi
    pop rdi
    xor rbx, rbx
    .loop:
        test al, al
        jz .finish
        cmp rbx, rsi
        je .abort
        cmp al, ' '
        je .finish
        cmp al, `\t`
        je .finish
        cmp al, `\n`
        je .finish
        mov [rdi + rbx], al
        inc rbx
        push rdi
        push rbx
        push rsi
        call read_char
        pop rsi
        pop rbx
        pop rdi
        jmp .loop
    .abort:
        xor rax, rax
        pop rbx
        ret
    .finish:
        cmp rbx, rsi
        je .abort
        mov rax, rdi
        mov rdx, rbx
        mov byte [rax + rbx], 0
        pop rbx
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
        jz .exit_loop
        cmp rcx, '0'
        jb .exit_loop
        cmp rcx, '9'
        ja .exit_loop
        sub rcx, '0'
        imul rax, rax, 10
        add rax, rcx
        inc rdx
        inc rdi
        jmp .loop
    .exit_loop:
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
    call parse_uint
    jmp .finish
    .positive:
        inc rdi
        call parse_uint
        test rdx, rdx
        jz .finish
        inc rdx
        jmp .finish
    .negative:
        inc rdi
        call parse_uint
        test rdx, rdx
        jz .finish
        inc rdx
        neg rax
    .finish:
        ret

; Принимает указатель на строку (rdi), указатель на буфер (rsi) и длину буфера (rdx)
; Копирует строку в буфер
; Возвращает длину строки если она умещается в буфер, иначе 0
string_copy:
    xor rax, rax
    xor rcx, rcx
    .loop:
        mov al, [rdi + rcx]
        mov [rsi + rcx], al
        test al, al
        jz .exit_loop
        inc rcx
        cmp rcx, rdx
        jae .fail
        jmp .loop
    .fail:
        xor rax, rax
        ret
    .exit_loop:
        mov rax, rcx
        ret

; hello_mmap.asm
%define O_RDONLY 0 
%define PROT_READ 0x1
%define MAP_PRIVATE 0x2
%define SYS_WRITE 1
%define SYS_OPEN 2
%define SYS_MMAP 9
%define FD_STDOUT 1
%define SYS_MUNMAP 11
%define SYS_CLOSE 3
%define FSTAT_BUF 144
%define SYS_FSTAT 5

section .bss
fstat: resb FSTAT_BUF

section .data
    ; This is the file name. You are free to change it.
    fname: db 'hello.txt', 0

section .text
global _start

; use exit system call to shut down correctly
exit:
    mov  rax, 60
    xor  rdi, rdi
    syscall

; These functions are used to print a null terminated string
; rdi holds a string pointer
print_string:
    push rdi
    call string_length
    pop  rsi
    mov  rdx, rax 
    mov  rax, SYS_WRITE
    mov  rdi, FD_STDOUT
    syscall
    ret

string_length:
    xor  rax, rax
.loop:
    cmp  byte [rdi+rax], 0
    je   .end 
    inc  rax
    jmp .loop 
.end:
    ret

; This function is used to print a substring with given length
; rdi holds a string pointer
; rsi holds a substring length
print_substring:
    mov  rdx, rsi 
    mov  rsi, rdi
    mov  rax, SYS_WRITE
    mov  rdi, FD_STDOUT
    syscall
    ret

_start:
    ; Вызовите open и откройте fname в режиме read only.
    mov  rax, SYS_OPEN
    mov  rdi, fname
    mov  rsi, O_RDONLY      ; Open file read only
    mov  rdx, 0 	        ; We are not creating a file
                            ; so this argument has no meaning
    syscall
    ; rax holds the opened file descriptor now

    mov r14, rax
	mov rdi, rax
	mov rax, SYS_FSTAT
	mov rsi, fstat
	syscall

    mov rdi, 0              ; Первый аргумент
    mov rsi, [fstat + 48]   ; Второй аргумент
    mov rdx, PROT_READ      ; Третий аргумент
    mov r10, MAP_PRIVATE    ; Четыертый аргумент
    mov r8, r14             ; Пятый аргумент
    mov r9, 0               ; Шестой аргумент
    mov rax, SYS_MMAP       ; Системный вызов MMAP
    syscall

    mov rdi, rax
    call print_string

    mov rax, SYS_MUNMAP
	mov rdi, r13
	mov rsi, [fstat + 48]
	syscall

	mov rax, SYS_CLOSE
	mov rdi, r14
	syscall

    ; Вызовите mmap c правильными аргументами
    ; Дайте операционной системе самой выбрать, куда отобразить файл
    ; Размер области возьмите в размер страницы 
    ; Область не должна быть общей для нескольких процессов 
    ; и должна выделяться только для чтения.


    ; с помощью print_string теперь можно вывести его содержимое

    call exit

                ORG 0x00FE              ; Адрес начала программы
STR_POINTER:    WORD 0x0573             ; Указатель на строку
END_CHAR:       WORD 0x000A             ; Стоп символ
CURRENT_WORD:   WORD 0x0000             ; Текущее слово
MASK:           WORD 0x00FF             ; маска

START:          LD STR_POINTER          ; Загрузка слова
                SWAB                    ; Обмен байт
                PUSH                    ; Сохранение в стек
                CALL OUT_CHAR           ; Вызов подпрограммы записи для старших 8 бит
                POP                     ;
                AND MASK                ; Проверка на стоп символ
                CMP END_CHAR            ; 
                BEQ EXIT                ;
                LD (STR_POINTER)+       ; Загрузка слова
                PUSH                    ; Сохранение в стек
                CALL OUT_CHAR           ; Вызов подпрограммы записи для младших 8 бит
                POP                     ; 
                AND MASK                ; Проверка на стоп символ
                CMP END_CHAR            ; Проверка на стоп символ
                BEQ EXIT                ;
                JUMP START              ; Безусловный переход в начало программы
EXIT:           HLT
                                        ; Подпрограмма вывода символа на устройство
OUT_CHAR:       IN 7                    ; Спин-луп по флагу ВУ-3
                AND #0x40               ; 
                BEQ OUT_CHAR            ; 
                LD &1                   ; Загрузка из стека
                OUT 6                   ; Вывод символа на устройство
                RET                     ; Выход из подпрограммы записи

ORG 0x0573
WORD 0xE2EE
WORD 0x20E2
WORD 0xF0E5
WORD 0xECE5
WORD 0xEDE0
WORD 0x20E0
WORD 0xEDE0
WORD 0xEBFC
WORD 0xEDFB
WORD 0xF520
WORD 0xECEE
WORD 0xE4E5
WORD 0xF0E0
WORD 0xF6E8
WORD 0xE90A
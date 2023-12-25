                ORG 0x056C              ; Адрес начала программы
STR_POINTER:    WORD 0x05D7             ; Указатель на строку
END_CHAR:       WORD 0x000D             ; Стоп символ
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
OUT_CHAR:       IN 3                    ; Спин-луп по флагу ВУ-1
                AND #0x40               ; 
                BEQ OUT_CHAR            ; 
                LD &1                   ; Загрузка из стека
                OUT 2                   ; Вывод символа на устройство
                RET                     ; Выход из подпрограммы записи
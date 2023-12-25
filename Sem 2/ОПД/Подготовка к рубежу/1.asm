; Задание: Подсчет суммы элементов массива, которые делятся на 17 

ORG             0x0010
WORD            0x0011
WORD            0x0000
WORD            0x0000
WORD            0x0022
WORD            0x0004
WORD            0x0033
WORD            0x0000
WORD            0x0000
WORD            0x0044
WORD            0x0011

ORG             0x0050
ARRAY: WORD     0x0010
CURRENT: WORD   0x0000
DIVIDER: WORD   0x0011
COUNTER: WORD   0x000A
RESULT: WORD    0x0000

START:          LD COUNTER
                DEC
                ST COUNTER
                BMI EXIT
                LD (ARRAY)+
                ST CURRENT
                BMI GET_ABS

DIVISION:       SUB DIVIDER
                BMI START
                BEQ ADD_TO_RES
                JUMP DIVISION

ADD_TO_RES:     LD RESULT
                ADD CURRENT
                ST RESULT
                JUMP START

GET_ABS:        NEG
                ST CURRENT
                JUMP DIVISION

EXIT:           LD RESULT
                HLT
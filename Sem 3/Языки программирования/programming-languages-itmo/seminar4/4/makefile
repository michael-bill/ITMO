CFLAGS     = -g -O2 -Wall -Werror -std=c17 -Wdiscarded-qualifiers -Wincompatible-pointer-types -Wint-conversion
CC         = gcc
LD         = gcc
ASM        = nasm
ASMFLAGS   =-felf64 -g

# Если есть код на C, то компоновку тоже нужно производить
# с помощью gcc, а не ld
main: main.o lib.o
	$(LD) -o $@ $^

# target : dependencies
#     do this
main.o: main.c 
	$(CC) -c $(CFLAGS) -o $@ $<

lib.o: lib.asm
	$(ASM) $(ASMFLAGS) -o $@ $<

clean: 
	$(RM) main main.o lib.o

.PHONY: clean

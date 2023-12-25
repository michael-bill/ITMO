ASM=nasm
ASMFLAGS=-f elf64
LD=ld
PORG_NAME=program

%.o: %.asm
	$(ASM) $(ASMFLAGS) -o $@ $<

program: lib.o main.o
	$(LD) -o $(PORG_NAME) main.o lib.o

.PHONY: clean
clean:
	rm *.o
ASM=nasm
ASMFLAGS=-f elf64
LD=ld
PORG_NAME=program

%.o: %.asm
	$(ASM) $(ASMFLAGS) -o $@ $<

program: hello_mmap.o
	$(LD) -o $(PORG_NAME) hello_mmap.o

.PHONY: clean
clean:
	rm *.o
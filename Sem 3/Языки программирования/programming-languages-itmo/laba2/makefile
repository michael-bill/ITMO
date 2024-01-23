ASM = nasm
FLAGS = -f elf64

%.o: %.asm *.inc
	$(ASM) $(FLAGS) -o $@ $<
main: main.o lib.o dict.o
	ld -o main main.o lib.o dict.o
clean:
	rm -rf *.o main
test: main
	python3 test.py

.PHONY: clean test
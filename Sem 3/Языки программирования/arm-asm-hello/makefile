ASM=as
ASMFLAGS=-arch arm64
LD=ld

%.o: %.s
	$(ASM) $(ASMFLAGS) -o $@ $<

program: hello.o
	$(LD) -o hello hello.o -lSystem -syslibroot `xcrun -sdk macosx --show-sdk-path` -e _start -arch arm64

.PHONY: cleanfile
clean:
	rm *.o

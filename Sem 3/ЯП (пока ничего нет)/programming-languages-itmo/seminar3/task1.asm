%macro task1 2-*
    %rep %0 - 1
        db %1
    %rotate 1
        db ","
    %endrep
        db %1
%endmacro

task1 "hello", "another", " world"

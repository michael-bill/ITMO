%define connection 0
%macro colon 2
    %2: dq connection
        db %1, 0
    %define connection %2
%endmacro
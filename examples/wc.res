
from error import errno, strerror as err, strerr
from io import fopen, printf

function fopen (filename: const string, mode: const string, error: int)
    ...
end function

from io import readlines

strings = readlines("hello and bye.txt")

function main (args: [const string])
    program_name = (args.len >= 1) ? args[0] : "cat"
    line_number = 0

    for i = 1; i < args.len; i += 1
        file = fopen(args[i], "r", error)
        if errno == 0
            do
                buffer = file.read()
                for j = 0; j < buffer.len; j += 1
                    if buffer[j] == '\n'
                        line_number += 1
                    end if
                end for
            while buffer.len != 0
            if errno != 0
                printf("error: while reading {}: {}", args[0], strerror())
                break
            end if
        end if
        printf("error: can't open {}: {}", args[0], strerror())
    end for

    printf(line_number)
end function


# slowest C Rest Compiler

import io

def tokenize(file):
    lnum = 0
    line = file.readline()
    while line:
        lnum += 1

        line = file.readline()


"""
' ' | '\t'


"""


# TODO: just get work done

def tokenizer(file, grammar):
    for line in file:

        while line:
            root = grammar

            for string, token in grammar.items():
                if line.starswith(string):
                    line = line[len(string):]
                    if isinstance(token, dict):
                        grammar = token
                        

            else:
                raise ValueError('unknown char: {!r}'.format(line))

keywords = [
    'goto', 'label',
    'if', 'then', 'else', 'end',
    'repeat', 'for', 'in', 'while',
    'switch', 'case', 'default',

    'break', 'continue',

    'var', 'const', 'let', 'ref',
    'def', 'yield', 'return',

    'type', 'untype',

    'struct', 'self',

    'true', 'false', 'null',
]

grammar = {
    ' ': '#ignore',
    '#': '#ignore_to_end',

    '=': {
        '=': '==',
        None: '='
    },

    '-': {
        '=': '-=',
        None: '-',
    }
}

for string in keywords:
    grammar[string] = string

for token in tokenizer(open('code.res'), grammar):
    print(repr(token))


--
builtins types:
    int                     123
    float                   1.23
    char                    'a'
    string                  "Hi"
    [int, char]             [10, 'a']            <- an array of one int and one
                                                    char
    array[int]              [10, 20, 30]         <- an array of ints
    {a: int, b: char}       {a = 10, b = 'a'}    <- a named tuple or structure
    function                def (a) = (a)        <- a lambda or function
    boolean                 true || false
    null                    null
--

# any value type can be stored in a
var a = 100     # comments
a = "Hi"        # another comment
a = 1.23

a = \\ multi line comments \\ ->
    123 ->
    * 456 # one line comments

##
    # hi another inline comment
multi line comments ##

#[ multi line comment ]
#[ ]

a = #[[ multi line comments ]]->
 ~ 123  # one line comment

type null_string = null | string

# c can be null or a string
var c: null_string = null
c = "Hi"

def type uint = (self ?= int) && (self >= 0)

# the following line will throw a compiler error
# var b: uint = -100

# immutable variable
const d = 10

# lazy evaluation variable
let e = c + " World"

c = "Hello"
var f = e       # f == "Hello World"

let g = c

g = "Goodbye"
f = e           # f == "Goodbye World"

def range(const start: int, const end: int; const step: int = 1): array[int]
    var i = start

    if step >= 0
        let not_end = (i < end)
    if step < 0
        let not_end = (i > end)
    end

    while not_end
        yield i
        i += step
    end
end

def sum(var *args: int): int
    var sum: int = 0
    for var i = 0; i < args.len; i += 1
        sum += args[i]
    end
    return sum
end

def main(args: array[string])

    # print sum of 1 to 500
    print(sum(*range(1, 500)))

end

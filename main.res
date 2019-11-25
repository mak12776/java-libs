
class Token

end

const NAME = "Amin";
var name = "amin";

let number => buffer[ch];
ref a => number;

number += 20;

enum TokenType
	NAME	VALUE
end

class View
	var start: int
	var end: int

	def View(start: int, end: int)

	end
end

class Token
	var type: TokenType
	var index: View
	var line: View

	def Token(type: TokenType, index: View, line: View)
		self.type = type
		self.index = index
		self.line = line
	end

	def Token(type: TokenType, start: int, end: int, start_line: int, end_line: int)
		self.type = type
		self.index = new View(start, end)
		self.line = new View(start_line, end_line)
	end
end

def parse(val buffer: string): Token[]
	var index = 0

	let ch = buffer[index]
	let is_end = (index >= buffer.length)
	def next() = (index += 1; is_end)

	if is_end then return

loop:
	if ch in [' ', '\t']
		index += 1
		if is_end then return

		goto loop
	end

	if ch == '='
		if next() then yield new Token(EQ, 0, 0, 0, 0)

	end

	if ch.is_lower()
		index += 1
		if is_end then 

	end


end

def main(args: string[])
	for arg in args
		if arg[0] == '-'
	end
end

class View
	var buffer: string
	var start: int
	var end: int

	def View(buffer: string, start: int, end: int)
		self.buffer = buffer
		self.start = start
		self.end = end
	end
end

def parse_buffer(buffer: string): [string, View, View][1:2:3]

	var index: int = 0

	let ch => buffer[index]
	let is_end => index == buffer.length

	if is_end then return

	if ch in [' ', '\t']
		index += 1
end

class Person
	val name: string
	val age: int

	def Person(name: string, age: int)
		self.name = name
		s

def sum(val *values; inital = 0)
	var total = initial
	for value in values
		total += value
	end
	return total
end

var sum = sum(1, 2, 3, 4)

name = "amin"
next = name[::2]

for i in (1:100:2)
	print(i)
end

print(sum(1:100:2))

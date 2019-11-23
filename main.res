
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
		if next() then yield new Token(EQ, )

	end

	if ch.is_lower()
		index += 1
		if is_end 

	end


end

def main(args: array<string>)

end

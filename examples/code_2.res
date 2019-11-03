
buffer, lines = read_lines("code.rest")

def lstrip_chr(buffer, buffer_index)
    buffer_index.start = buffer
end

struct buffer_index
    ref buffer: string
    var start: int
    var end: int
    let length: int = end - start

    def find(test: function[char; boolean]): int
        var index = self.start
        while index < end
            if test(self.buffer[index])
                return index
            end
        end
        return end
    end

    def lstrip(test: function[char; boolean])
        self.start = self.find(test)
    end

end

block lstrip_chr(ref buffer, ref view)
    while view.start < view.end
        if buffer[view.start] in string
            break
        end
        view.start += 1
    end
end

for var i = 0; i < lines.total; i += 1
    memory view = lines.pntr[i]

    block lstrip_chr(buffer, view, string)
        while view.start < view.end
            if buffer[view.start] in string
                break
            end
            view.start += 1
        end
    end

    lstrip_chr()

end

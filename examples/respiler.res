
def parse_buffer (buffer: string)
    var index = 0
    let ch = buffer[index]

    def next_index (): boolean
        index += 1
        return index == buffer.length
    end

    while ch in [' ', '\t']
        if next_index()
            return null
        end
    end

    if ch.is_lower()
        if ch.is_upper()
        end
    end

end

def parse (file_name: string)

end

def main ()
    file_name = "code.rest"


end

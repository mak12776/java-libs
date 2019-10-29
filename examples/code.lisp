(set a (+ 10 100))
(set b (+ a 10 20))
(print b)
(set file (open "code.rest" "rb"))

(set i 0)
(set buffer (file.read buffer_size))
(let ch ($ buffer i))
(let end (= i buffer.len))
(def in_range [ch start end] (<= start ch end))
(let inc_index (set i (+ i 1)))

start:
(if end (jump end))

(set i (find_chr buffer " \t"))


(if (in " \t") )

(jump start)
end:

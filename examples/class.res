
[ private ]

class Point
    var x: int
    var y: int

    [ public ]

    function self (x: int, y: int)
        self.x = x
        self.y = y
    end

    function add (point: Point)
        self.x += point.x
        self.y += point.y
    end

    function mul (point: Point)
        self.x *= point.x
        self.y *= point.y
    end

    function distance (point: Point): float
        return ((self.x - point.x) ** 2 + (self.y - point.y) ** 2) ** (1 / 2)
    end
end

[ public ]

struct Point
    x: int
    x2: int => x

    function add (point: Point)
        self.x += point.x
        self.y += point.y
    end

    function sub (point: Point)
        self.x -= point.x
        self.y -= point.y
    end

    function mul (point: Point)
        self.x *= point.x
        self.y *= point.y
    end

    function distance (point: Point): float
        return ((self.x - point.x) ** 2 + (self.y - point.y) ** 2) ** (1 / 2)
    end
end

function main ()
    var point1 = new Point {x = 10, y = 30}

    let point2 = point1
end

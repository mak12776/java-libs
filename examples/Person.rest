
class Person
    private
        name: string
        age: int

    public
        def Person(name: string, age: int)
            self.name = name
            self.age = age
        end

        def Hello()
            print('Hello, my name is #{self.name}')
        end
end

struct Person
    name: string
    age: int

    def Hello()
        print('Hello, my name is #{self.name}')
    end
end

me = new Person("Amin", 21)
me = new Person(name = "Amin", age = 21)

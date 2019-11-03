
def main(args)
    if args[1] == 'Hi' then
        goto Home
    else
        goto School
    end

    label Home
    do_something_great()
    goto end

    label School
    do_something_else()
    goto end

    label end
end

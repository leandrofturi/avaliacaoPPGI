argv = map(x->string(x), ARGS)

for i in argv
    println(i)
end
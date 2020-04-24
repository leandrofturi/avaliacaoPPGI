println("Inicializando JULIA")
include("PPGI.jl")

argv = map(x->string(x), ARGS)

# Em um escopo local, como no loop for, variáveis ​​globais são herdadas
# apenas para leitura, mas não para escrita.
let
    if size(argv, 1) == 12
        path_docentes = ""
        path_veiculos = ""
        path_qualis = ""
        path_publicacoes = ""
        path_regras = ""
        ano_ref = 0
        for i in 1:(size(argv, 1)-1)
            if argv[i] == "-d"
                path_docentes = argv[i+1]
            elseif argv[i] == "-v"
                path_veiculos = argv[i+1]
            elseif argv[i] == "-p"
                path_publicacoes = argv[i+1]
            elseif argv[i] == "-q"
                path_qualis = argv[i+1]
            elseif argv[i] == "-r"
                path_regras = argv[i+1]
            elseif argv[i] == "-a"
                ano_ref = parse(Int, argv[i+1])
            end
        end
        manual(path_docentes, path_veiculos, path_qualis,
           path_publicacoes, path_regras, ano_ref)
    else
        println("Iterativo")
        iterativo()
    end
end
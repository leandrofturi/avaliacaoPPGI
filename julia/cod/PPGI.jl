using Base, DataFrames, CSV, Gtk
include("Conferencia.jl")
include("Docente.jl")
include("Periodico.jl")
include("PontuadorPPGI.jl")
include("PublicacaoConferencia.jl")
include("PublicacaoPeriodico.jl")

mutable struct PPGI
    docentes::Array{Docente}
	veiculos::Array{Veiculo}
	publicacoes::Array{Publicacao}
    pontuadores::Array{PontuadorPPGI}
    coordenador::Docente

    PPGI() = new(Docente[], Veiculo[], Publicacao[], PontuadorPPGI[], Docente())
end

function add_docente(ppgi::PPGI, d::Docente)
    for e in ppgi.docentes
        if iguais(d, e)
            return
        end
    end
    push!(ppgi.docentes, d)
end

function add_veiculo(ppgi::PPGI, v::Veiculo)
    for w in ppgi.veiculos
        if iguais(v, w)
            return
        end
    end
    push!(ppgi.veiculos, v)
end

function add_publicacao(ppgi::PPGI, p::Publicacao)
    for q in ppgi.publicacoes
        if iguais(p, q)
            return
        end
    end
    push!(ppgi.publicacoes, p)
end

function add_pontuador(ppgi::PPGI, p::PontuadorPPGI)
    for q in ppgi.pontuadores
        if iguais(p, q)
            return
        end
    end
    push!(ppgi.pontuadores, p)
end

function find_veiculo(ppgi::PPGI, sigla::String)
    for v in ppgi.veiculos
        if v.sigla == sigla
            return v
        end
    end
end

function find_docente(ppgi::PPGI, codigo::Int64)
    for d in ppgi.docentes
        if d.codigo == codigo
            return d
        end
    end
end

function make_autores(ppgi::PPGI, nomes::String)
    a = Docente[]
    for n in split(nomes, ", ")
        push!(a, find_docente(ppgi, parse(Int64, n)))
    end
    return a
end

function make_qualis(qualis::String, pontos::String)
    q = split(qualis, ",")
    p = split(pontos, ",")
    r = Tuple{String, Int64}[]
    for i in 1:min(size(q, 1), size(p, 1))
        push!(r, (q[i], parse(Int64, p[i])))
    end
    return r
end

function publicacoes_por_docente(ppgi::PPGI, docente::Docente)
    p = Publicacao[]
    for q in ppgi.publicacoes
        for r in q.autores
            if iguais(docente, r)
                push!(p, q)
            end
        end
    end
    return p
end

function publicacoes_por_qualis(ppgi::PPGI, qualis::String, ano_ref::Int64)
    p = Publicacao[]
    for q in ppgi.publicacoes
        if get_qualis(q, ano_ref) == qualis
            push!(p, q)
        end
    end
    return p
end

function get_pontuador(ppgi::PPGI, ano_ref::Int64)
    for p in ppgi.pontuadores
        if p.inicio <= Date(ano_ref) && p.fim >= Date(ano_ref)
            return p
        end
    end
end

function calcula_pontuacao(ppgi::PPGI, docente::Docente, ano_ref::Int64)
    pontuador = get_pontuador(ppgi, ano_ref)
    r = 0.0
    for p in publicacoes_por_docente(ppgi, docente)
        if (Year(Date(ano_ref))-Year(p.ano)).value  <= pontuador.anos_a_considerar &&
            Date(ano_ref) > p.ano
            if typeof(p) == PublicacaoPeriodico
                r = r + pontuador.multiplicador*get_pontuacao(pontuador, get_qualis(p, ano_ref))
            elseif typeof(p) == PublicacaoConferencia
                r = r + get_pontuacao(pontuador, get_qualis(p, ano_ref))
            end
        end
    end
    return r
end

function boas_vindas()
    println()
    println("─▄▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▄")
    println("█░░░█░░░░░░░░░░▄▄░██░█")
    println("█░▀▀█▀▀░▄▀░▄▀░░▀▀░▄▄░█")
    println("█░░░▀░░░▄▄▄▄▄░░██░▀▀░█")
    println("─▀▄▄▄▄▄▀─────▀▄▄▄▄▄▄▀")
    println("Bem-vindo ao sistema do PPGI!")
    println("Aqui você pode carregar arquivos específicos e gerar arquivos mais específicos ainda.")
    println()
end

function carrega_docentes(ppgi::PPGI, path::String)
    df = CSV.read(path, copycols=true)
    for i in 1:size(df, 1)
        add_docente(ppgi, Docente(df[i,1], df[i,2], df[i,3], df[i,4]))
        if !ismissing(df[i,5])
            ppgi.coordenador = Docente(df[i,1], df[i,2], df[i,3], df[i,4])
        end
    end
end

function carrega_veiculos(ppgi::PPGI, path::String)
    df = CSV.read(path, copycols=true)
    for i in 1:size(df, 1)
        if df[i,3] == "P"
            add_veiculo(ppgi, Periodico(df[i,1], df[i,2], Float64(df[i,4]), df[i,5]))
        end
        if df[i,3] == "C"
            add_veiculo(ppgi, Conferencia(df[i,1], df[i,2], Float64(df[i,4])))
        end
    end
end

function carrega_qualis_veiculos(ppgi::PPGI, path::String)
    df = CSV.read(path, copycols=true)
    for i in 1:size(df, 1)
        add_qualis(find_veiculo(ppgi, df[i,2]), df[i,1], df[i,3])
    end
end

function carrega_publicacoes(ppgi::PPGI, path::String)
    df = CSV.read(path, copycols=true)
    for i in 1:size(df, 1)
        if ismissing(df[i,7])
            add_publicacao(ppgi, PublicacaoPeriodico(df[i,1], find_veiculo(ppgi, df[i,2]),
            df[i,3], make_autores(ppgi, df[i,4]), df[i,8], df[i,9], df[i,5], df[i,6]))
        elseif ismissing(df[i,6])
            add_publicacao(ppgi, PublicacaoConferencia(df[i,1], find_veiculo(ppgi, df[i,2]),
            df[i,3], make_autores(ppgi, df[i,4]), df[i,8], df[i,9], df[i,5], df[i,7]))
        end
    end
end

function carrega_pontuadores(ppgi::PPGI, path::String)
    df = CSV.read(path, copycols=true)
    sort!(df, [1])
    for i in 1:size(df, 1)
        add_pontuador(ppgi, PontuadorPPGI(df[i,1], df[i,2], make_qualis(df[i,3], df[i,4]),
        Float64(df[i,5]), df[i,6], Float64(df[i,7])))
    end
end

function escreve_recredenciamento(ppgi::PPGI, ano_ref::Int64, path::String)
    df = DataFrame(Nome_do_docente=String[], Pontuacao_alcancada=Float64[],
        Recredenciado=String[])
    for d in ppgi.docentes
        p = round(calcula_pontuacao(ppgi, d, ano_ref), digits=1)
        if iguais(ppgi.coordenador, d)
            push!(df, [d.nome, p, "Coordenador"])
        elseif tempo_ingresso(d, ano_ref) <= 3
            push!(df, [d.nome, p, "PPJ"])
        elseif idade(d, ano_ref) > 60
            push!(df, [d.nome, p, "PPS"])
        elseif p >= get_pontuador(ppgi, ano_ref).min_recredenciamento
            push!(df, [d.nome, p, "Sim"])
        else
            push!(df, [d.nome, p, "Não"])
        end
    end
    sort!(df, :Nome_do_docente)
    CSV.write(path, df)
end

function escreve_publicacoes(ppgi::PPGI, ano_ref::Int64, path::String)
    df = DataFrame(Ano=Int64[], Sigla_veiculo=String[], Veiculo=String[],
        Qualis=String[], Fator_de_impacto=Float64[], Publicacao=String[],
        Docentes=String[])
    for p in ppgi.publicacoes
        push!(df, [Year(p.ano).value, p.veiculo.sigla, p.veiculo.nome,
            get_qualis(p, ano_ref), round(p.veiculo.impacto, digits=3),
            p.titulo, make_nome_autores(p)])
    end
    sort!(df, (:Qualis, order(:Ano, rev=true), :Sigla_veiculo, :Publicacao))
    CSV.write(path, df)
end

function escreve_estatisticas(ppgi::PPGI, ano_ref::Int64, path::String)
    df = DataFrame(Qualis=String[], Numero_artigos=Int64[],
        Numero_artigos_por_docente=Float64[])
    for q in ["A1","A2","B1","B2","B3","B4","B5","C"]
        p = publicacoes_por_qualis(ppgi, q, ano_ref)
        n = 0.0
        for r in p
            n = n + 1/size(r.autores, 1)
        end
        push!(df, [q, size(p, 1), round(n, digits=2)])
    end
    CSV.write(path, df)
end

function manual(path_docentes::String, path_veiculos::String, path_qualis::String,
    path_publicacoes::String, path_regras::String, ano_ref::Int64)
    boas_vindas()

    ppgi = PPGI()

    println("Carregando arquivos...")
    carrega_docentes(ppgi, path_docentes)
    carrega_veiculos(ppgi, path_veiculos)
    carrega_qualis_veiculos(ppgi, path_qualis)
    carrega_publicacoes(ppgi, path_publicacoes)
    carrega_pontuadores(ppgi, path_regras)

    println("Escrevendo arquivos...")
    mkdir("out")
    escreve_recredenciamento(ppgi, ano_ref, "out/1-recredenciamento.csv")
    escreve_publicacoes(ppgi, ano_ref, "out/2-publicacoes.csv")
    escreve_estatisticas(ppgi, ano_ref, "out/3-estatisticas.csv")
    println("Obrigado por utilizar nossos serviços!")
end

function iterativo()
    boas_vindas()

    ppgi = PPGI()

    println("Carregando arquivos...")
    carrega_docentes(ppgi, open_dialog("Arquivo docentes"))
    carrega_veiculos(ppgi, open_dialog("Arquivo veiculos"))
    carrega_qualis_veiculos(ppgi, open_dialog("Arquivo qualis"))
    carrega_publicacoes(ppgi, open_dialog("Arquivo publicacoes"))
    carrega_pontuadores(ppgi, open_dialog("Arquivo pontuadores"))

    println("Ano de referência:")
    ano_ref = readline()
    ano_ref = parse(Int, chomp(ano_ref))

    println("Escrevendo arquivos...")
    if isdir("out")
        rm("out", recursive=true)
    end
    mkdir("out")

    escreve_recredenciamento(ppgi, ano_ref, "out/1-recredenciamento.csv")
    escreve_publicacoes(ppgi, ano_ref, "out/2-publicacoes.csv")
    escreve_estatisticas(ppgi, ano_ref, "out/3-estatisticas.csv")
    println("Obrigado por utilizar nossos serviços!")
end
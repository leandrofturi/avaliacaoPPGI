using Base, Dates
include("Docente.jl")
include("Veiculo.jl")

abstract type Publicacao end

macro def(name, definition)
    return quote
        macro $(esc(name))()
            esc($(Expr(:quote, definition)))
        end
    end
end

@def atributos_Publicacao begin
    ano::Date
	veiculo::Veiculo
	titulo::String
	autores::Array{Docente}
	pg_inicial::Int64
	pg_final::Int64
end

ano(p::Publicacao) = p.ano
veiculo(p::Publicacao) = p.veiculo
titulo(p::Publicacao) = p.titulo
autores(p::Publicacao) = p.autores
pg_inicial(p::Publicacao) = p.pg_inicial
pg_final(p::Publicacao) = p.pg_final

function get_qualis(p::Publicacao, ano::Int64)
    get_qualis(p.veiculo, ano)
end

function make_nome_autores(p::Publicacao)
    r = p.autores[1].nome
    if size(p.autores, 1) > 1
        for d in 2:size(p.autores, 1)
            r = r * ", " * p.autores[d].nome
        end
    end
    return r
end

function iguais(a::Array{Docente}, b::Array{Docente})
    for c in a
        if !in(c, b)
            return false
        end
    end
    for c in b
        if !in(c, a)
            return false
        end
    end
    return true
end

function iguais(p::Publicacao, q::Publicacao)
    p.ano == q.ano && iguais(p.veiculo, q.veiculo) && p.titulo == q.titulo &&
    iguais(p.autores, q.autores) && p.pg_inicial == q.pg_inicial && p.pg_final == q.pg_final
end
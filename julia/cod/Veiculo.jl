using Base

abstract type Veiculo end

macro def(name, definition)
    return quote
        macro $(esc(name))()
            esc($(Expr(:quote, definition)))
        end
    end
end

@def atributos_Veiculo begin
    sigla::String
	nome::String
    impacto::Float64
    qualis::Array{Tuple{Int64, String}}
end

sigla(v::Veiculo) = v.sigla
nome(v::Veiculo) = v.nome
impacto(v::Veiculo) = v.impacto

function add_qualis(v::Veiculo, ano::Int64, qualis::String)
    for q in v.qualis
        if q[1] == ano && q[2] == qualis
            return
        end
    end
    push!(v.qualis, (ano, qualis))
end

function get_qualis(v::Veiculo, ano::Int64)
    for q in v.qualis
        if q[1] < ano
            return q[2]
        end
    end
    v.qualis[1][2]
end

function iguais(v::Veiculo, w::Veiculo)
    v.sigla == w.sigla && v.nome == w.nome && v.impacto == w.impacto
end
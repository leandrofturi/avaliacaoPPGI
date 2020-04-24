using Base, Dates

struct PontuadorPPGI
    inicio::Date
	fim::Date
	qualis::Array{Tuple{String, Int64}}
	multiplicador::Float64
	anos_a_considerar::Int64
    min_recredenciamento::Float64
    
    PontuadorPPGI(inicio::String, fim::String, qualis::Array{Tuple{String, Int64}}, multiplicador::Float64,
    anos_a_considerar::Int64, min_recredenciamento::Float64) =
    new(Dates.DateTime(inicio, "dd/mm/yyyy"), Dates.DateTime(fim, "dd/mm/yyyy"),
    qualis, multiplicador, anos_a_considerar, min_recredenciamento)
end

inicio(p::PontuadorPPGI) = p.inicio
fim(p::PontuadorPPGI) = p.fim
multiplicador(p::PontuadorPPGI) = p.multiplicador
anos_a_considerar(p::PontuadorPPGI) = p.anos_a_considerar
min_recredenciamento(p::PontuadorPPGI) = p.min_recredenciamento

function menor_qualis(q::String, r::String)
    last(findall(x->x == q, ["A1","A2","B1","B2","B3","B4","B5","C"])) >
    last(findall(x->x == r, ["A1","A2","B1","B2","B3","B4","B5","C"]))
end

function get_pontuacao(p::PontuadorPPGI, qualis::String)
    pontuacao = 0
    for s in p.qualis
        if !menor_qualis(s[1], qualis)
            pontuacao = s[2]
        else
            break
        end
    end
    return pontuacao
end

function iguais(p::PontuadorPPGI, q::PontuadorPPGI)
    p.inicio == q.inicio && p.fim == q.fim && p.multiplicador == q.multiplicador &&
	p.anos_a_considerar == q.anos_a_considerar && p.min_recredenciamento == q.min_recredenciamento
end
using Base
include("Publicacao.jl")

struct PublicacaoPeriodico <: Publicacao
    @atributos_Publicacao
    numero::Int64
    volume::Int64

    PublicacaoPeriodico(ano::Int, veiculo::Veiculo, titulo::String,
    autores::Array{Docente}, pg_inicial::Int64, pg_final::Int64,
    numero::Int64, volume::Int64) = new(Date(ano), veiculo, titulo,
    autores, pg_inicial, pg_final, numero, volume)
end

numero(p::PublicacaoPeriodico) = p.numero
volume(p::PublicacaoPeriodico) = p.volume
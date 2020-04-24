using Base
include("Publicacao.jl")

struct PublicacaoConferencia <: Publicacao
    @atributos_Publicacao
    numero::Int64
    local_ocor::String

    PublicacaoConferencia(ano::Int, veiculo::Veiculo, titulo::String,
    autores::Array{Docente}, pg_inicial::Int64, pg_final::Int64,
    numero::Int64, local_ocor::String) = new(Date(ano), veiculo, titulo,
    autores, pg_inicial, pg_final, numero, local_ocor)
end

numero(p::PublicacaoConferencia) = p.numero
local_ocor(p::PublicacaoConferencia) = p.local_ocor
using Base
include("Veiculo.jl")

struct Periodico <: Veiculo
    @atributos_Veiculo
    issn::String

    Periodico(sigla::String, nome::String, impacto::Float64, issn::String) = 
        new(sigla, nome, impacto, Tuple{Int64, String}[], issn)
end

issn(p::Periodico) = p.issn
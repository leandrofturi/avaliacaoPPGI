using Base
include("Veiculo.jl")

struct Conferencia <: Veiculo
    @atributos_Veiculo

    Conferencia(sigla::String, nome::String, impacto::Float64) = 
        new(sigla, nome, impacto, Tuple{Int64, String}[])
end
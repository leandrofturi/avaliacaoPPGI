using Base, Dates

struct Docente
    codigo::Int64
	nome::String
	nascimento::Date
    ingresso::Date
	
    Docente(codigo::Int64, nome::String, nascimento::String, ingresso::String) =
        new(codigo, nome, Dates.DateTime(nascimento, "dd/mm/yyyy"), Dates.DateTime(ingresso, "dd/mm/yyyy"))
    Docente() = new()
end

codigo(d::Docente) = d.codigo
nome(d::Docente) = d.nome
nascimento(d::Docente) = d.nascimento
ingresso(d::Docente) = d.ingresso
        
function idade(d::Docente, ano_ref::Int64)
    (Year(Date(ano_ref)) - Year(d.nascimento)).value
end
       
function tempo_ingresso(d::Docente, ano_ref::Int64)
    (Year(Date(ano_ref)) - Year(d.ingresso)).value
end

function iguais(d::Docente, e::Docente)
    d.codigo == e.codigo && d.nome == e.nome &&
    d.nascimento == e.nascimento && d.ingresso == e.ingresso
end
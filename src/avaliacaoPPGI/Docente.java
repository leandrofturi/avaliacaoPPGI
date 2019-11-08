package avaliacaoPPGI;

import java.util.Date;

public class Docente {
	
	int codigo;
	String nome;
	Date dataNascimento;
	Date dataIngresso;
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public Date getDataIngresso() {
		return dataIngresso;
	}
	
	public void setDataIngresso(Date dataIngresso) {
		this.dataIngresso = dataIngresso;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Docente other = (Docente) obj;
		if (codigo != other.codigo)
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "Docente [codigo=" + codigo + ", nome=" + nome + ", dataDeNascimento=" + dataNascimento + ", dataDeIngresso="
				+ dataIngresso + "]";
	}
	
	
}

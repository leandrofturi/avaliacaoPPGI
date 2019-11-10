package avaliacaoPPGI;

import java.util.Date;

class Docente {
	
	private Long codigo;
	private String nome;
	private Date dataNascimento;
	private Date dataIngresso;
	
	public Docente(Long codigo, String nome, Date dataNascimento, Date dataIngresso) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.dataIngresso = dataIngresso;
	}

	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
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

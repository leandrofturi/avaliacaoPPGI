package avaliacaoPPGI;

import java.io.Serializable;
import java.util.ArrayList;

abstract class Publicacao implements Serializable {

	protected int ano;
	protected Veiculo veiculo;
	protected String titulo;
	protected ArrayList<Docente> autores = new ArrayList<Docente>();
	protected int paginaInicial;
	protected int paginaFinal;
	
	private static final long serialVersionUID = -8591476662637344735L;

	public Publicacao(int ano, Veiculo veiculo, String titulo, ArrayList<Docente> autores, int paginaInicial,
			int paginaFinal) {
		super();
		this.ano = ano;
		this.veiculo = veiculo;
		this.titulo = titulo;
		this.autores = autores;
		this.paginaInicial = paginaInicial;
		this.paginaFinal = paginaFinal;
	}

	public int getAno() {
		return ano;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
	public String getQualis() {
		return (this.veiculo).getQualis(this.ano);
	}

	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public ArrayList<Docente> getAutores() {
		return this.autores;
	}
	
	public void addAutor(Docente autor) {
		if(!this.autores.contains(autor))
			this.autores.add(autor);
	}
	
	public boolean isAutor(Docente autor) {
		return this.autores.contains(autor);
	}
	
	public int getPaginaInicial() {
		return paginaInicial;
	}
	
	public void setPaginaInicial(int paginaInicial) {
		this.paginaInicial = paginaInicial;
	}
	
	public int getPaginaFinal() {
		return paginaFinal;
	}
	
	public void setPaginaFinal(int paginaFinal) {
		this.paginaFinal = paginaFinal;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publicacao other = (Publicacao) obj;
		if (ano != other.ano)
			return false;
		if (autores == null) {
			if (other.autores != null)
				return false;
		} else if (!autores.equals(other.autores))
			return false;
		if (paginaFinal != other.paginaFinal)
			return false;
		if (paginaInicial != other.paginaInicial)
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (veiculo == null) {
			if (other.veiculo != null)
				return false;
		} else if (!veiculo.equals(other.veiculo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Publicacao [ano=" + ano + ", veiculo=" + veiculo + ", titulo=" + titulo + ", autores=" + autores
				+ ", paginaInicial=" + paginaInicial + ", paginaFinal=" + paginaFinal + "]";
	}

}

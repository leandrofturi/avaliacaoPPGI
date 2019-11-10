package avaliacaoPPGI;

import java.util.ArrayList;

abstract class Publicacao {

	protected int ano;
	protected Veiculo veiculo;
	protected String titulo;
	protected ArrayList<Docente> autores = new ArrayList<Docente>();
	protected int paginaInicial;
	protected int paginaFinal;

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

	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Docente getAutor(Long codigo) {
		return null;
	}
	
	public void addAutor(Docente autor) {
		if(!this.autores.contains(autor))
			this.autores.add(autor);
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

}

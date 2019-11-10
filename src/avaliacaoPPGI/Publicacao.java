package avaliacaoPPGI;

import java.util.ArrayList;

class Publicacao {

	private int ano;
	Veiculo veiculo;
	private String titulo;
	private ArrayList<Docente> autores = new ArrayList<Docente>();
	private int numero;
	private int volume;
	private String localConferencia;
	private int paginaInicial;
	private int paginaFinal;

	public Publicacao(int ano, Veiculo veiculo, String titulo, ArrayList<Docente> autores, int numero, int volume,
			String localConferencia, int paginaInicial, int paginaFinal) {
		super();
		this.ano = ano;
		this.veiculo = veiculo;
		this.titulo = titulo;
		this.autores = autores;
		this.numero = numero;
		this.volume = volume;
		this.localConferencia = localConferencia;
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
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public int getVolume() {
		return volume;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public String getLocalConferencia() {
		return localConferencia;
	}
	
	public void setLocalConferencia(String localConferencia) {
		this.localConferencia = localConferencia;
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
	public String toString() {
		return "Publicacao [ano=" + ano + ", titulo=" + titulo + ", autores=" + autores
				+ ", numero=" + numero + ", volume=" + volume + ", localConferencia=" + localConferencia
				+ ", paginaInicial=" + paginaInicial + ", paginaFinal=" + paginaFinal + "]";
	}
	
}

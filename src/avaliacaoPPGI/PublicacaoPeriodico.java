package avaliacaoPPGI;

import java.util.ArrayList;

class PublicacaoPeriodico extends Publicacao {

	private int numero;
	private int volume;
	
	public PublicacaoPeriodico(int ano, Veiculo veiculo, String titulo, ArrayList<Docente> autores, 
			int numero, int volume, int paginaInicial, int paginaFinal) {
		super(ano, veiculo, titulo, autores, paginaInicial, paginaFinal);
		this.numero = numero;
		this.volume = volume;
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

	@Override
	public String toString() {
		return "PublicacaoPeriodico [numero=" + numero + ", volume=" + volume + ", ano=" + ano + ", veiculo=" + veiculo
				+ ", titulo=" + titulo + ", autores=" + autores + ", paginaInicial=" + paginaInicial + ", paginaFinal="
				+ paginaFinal + "]";
	}

}

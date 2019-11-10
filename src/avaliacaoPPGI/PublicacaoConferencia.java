package avaliacaoPPGI;

import java.util.ArrayList;

public class PublicacaoConferencia extends Publicacao {

	private int numero;
	private String localConferencia;
	
	public PublicacaoConferencia(int ano, Veiculo veiculo, String titulo, ArrayList<Docente> autores, 
			int numero, String localConferencia, int paginaInicial, int paginaFinal) {
		super(ano, veiculo, titulo, autores, paginaInicial, paginaFinal);
		this.numero = numero;
		this.localConferencia = localConferencia;
	}
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public String getLocalConferencia() {
		return localConferencia;
	}
	
	public void setLocalConferencia(String localConferencia) {
		this.localConferencia = localConferencia;
	}
	
	@Override
	public String toString() {
		return "PublicacaoConferencia [veiculo=" + veiculo + "numero=" + numero + ", localConferencia=" + localConferencia + "]";
	}
	
}

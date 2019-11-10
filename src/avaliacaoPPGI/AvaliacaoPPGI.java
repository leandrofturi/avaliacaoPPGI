package avaliacaoPPGI;

import java.io.IOException;

public class AvaliacaoPPGI {

	public static void main(String[] args) throws IOException {
		
		SistemaPPGI sis = new SistemaPPGI();
		sis.carregaArquivoDocentes("entradas/01/in/docentes.csv");
		sis.carregaArquivoVeiculos("entradas/01/in/veiculos.csv");
		sis.carregaArquivoQualificacoes("entradas/01/in/qualis.csv");
	}

}

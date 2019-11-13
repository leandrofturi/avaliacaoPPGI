package avaliacaoPPGI;

import java.io.IOException;

import exceptions.ErroDeFormatacao;
import exceptions.ErroDeIO;

public class AvaliacaoPPGI {

	public static void main(String[] args) throws IOException, ErroDeFormatacao, ErroDeIO {
		
		PPGI sis = new PPGI();
		sis.carregaArquivoDocentes("entradas/01/in/docentes.csv");
		sis.carregaArquivoVeiculos("entradas/01/in/veiculos.csv");
		sis.carregaArquivoQualificacoes("entradas/01/in/qualis.csv");
		sis.carregaArquivoPublicacoes("entradas/01/in/publicacoes.csv");
		sis.carregaArquivoPontuacoes("entradas/01/in/regras.csv");
		sis.imprimeDocentes();
		sis.imprimePublicacoes();
		sis.imprimeVeiculos();
		
		sis.escreveArquivoRecredenciamento("teste.csv");
	}

}

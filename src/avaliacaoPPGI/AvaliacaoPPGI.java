package avaliacaoPPGI;

import java.io.IOException;

import exceptions.*;

public class AvaliacaoPPGI {

	public static void main(String[] args) throws IOException, ErroDeFormatacao, ErroDeIO, CodigoRepetido, VeiculoDesconhecido, SiglaVeiculoNaoDefinida, CodSiglaNaoDefinido, QualiDesconhecidoVeiculo, QualiDesconhecidoRegra {
		
		PPGI sis = new PPGI();
		sis.carregaArquivoDocentes("entradas/script-java/testes/01/in/docentes.csv");
		sis.carregaArquivoVeiculos("entradas/script-java/testes/01/in/veiculos.csv");
		sis.carregaArquivoQualificacoes("entradas/script-java/testes/01/in/qualis.csv");
		sis.carregaArquivoPublicacoes("entradas/script-java/testes/01/in/publicacoes.csv");
		sis.carregaArquivoPontuacoes("entradas/script-java/testes/01/in/regras.csv");
		//sis.imprimeDocentes();
		//sis.imprimePublicacoes();
		//sis.imprimeVeiculos();
		//sis.imprimePontuadores();
		
		sis.escreveArquivoRecredenciamento(2016);
		sis.escreveArquivoPublicacoes();
		sis.escreveArquivoEstatisticas();
	}

}

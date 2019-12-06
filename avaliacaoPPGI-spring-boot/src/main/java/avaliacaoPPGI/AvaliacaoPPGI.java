package avaliacaoPPGI;

import java.io.IOException;

import exceptions.*;
import utils.CSVmanager;

public class AvaliacaoPPGI {

	public static void run() throws ErroDeIO, ErroDeFormatacao, CodigoRepetido, VeiculoDesconhecido, SiglaVeiculoNaoDefinida, QualiDesconhecidoVeiculo, Desconhecido, CodNaoDefinido, QualiDesconhecidoRegra {

		PPGI sistema = new PPGI();
		
		String pathDocentes = "upload-dir/in/docentes.csv";
		String pathVeiculos = "upload-dir/in/veiculos.csv";
		String pathPublicacoes = "upload-dir/in/publicacoes.csv";
		String pathQualificacoes = "upload-dir/in/qualis.csv";
		String pathRegras = "upload-dir/in/regras.csv";
		String pathanoRecredenciamento = "upload-dir/in/ano.csv";
		Integer anoRecredenciamento;

		sistema.carregaArquivoDocentes(pathDocentes);
		sistema.carregaArquivoVeiculos(pathVeiculos);
		sistema.carregaArquivoQualificacoes(pathQualificacoes);
		sistema.carregaArquivoPublicacoes(pathPublicacoes);
		sistema.carregaArquivoPontuacoes(pathRegras);
		try {
			anoRecredenciamento = Integer.parseInt((CSVmanager.CSVread(pathanoRecredenciamento, ';', false).get(0))[0]);
		} catch (NumberFormatException | IOException e) {
			throw new ErroDeFormatacao();
		}
			
		sistema.escreveArquivoRecredenciamento(anoRecredenciamento, "upload-dir/1-recredenciamento.csv");
		sistema.escreveArquivoPublicacoes("upload-dir/2-publicacoes.csv");
		sistema.escreveArquivoEstatisticas("upload-dir/3-estatisticas.csv");

	}

}
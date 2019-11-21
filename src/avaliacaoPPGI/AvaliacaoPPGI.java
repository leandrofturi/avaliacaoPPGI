package avaliacaoPPGI;

import exceptions.*;

public class AvaliacaoPPGI {

	public static void main(String[] args) {

		//-d entradas/script-java/testes/01/in/docentes.csv -v entradas/script-java/testes/01/in/veiculos.csv -p entradas/script-java/testes/01/in/publicacoes.csv -q entradas/script-java/testes/01/in/qualis.csv -r entradas/script-java/testes/01/in/regras.csv -a 2017
		
		PPGI sistema = new PPGI();
		
		String flag = null;
		String pathDocentes = null;
		String pathVeiculos = null;
		String pathPublicacoes = null;
		String pathQualificacoes = null;
		String pathRegras = null;
		Integer anoRecredenciamento = null;
		
		for(int i = 0; i < args.length-1; i++) {
			if(args[i].equals("--read-only")) {
				if(flag == null)
					flag = "R";
				else
					System.err.println(new Desconhecido().getMessage());
			}
			else if(args[i].equals("--write-only")) {
				if(flag == null)
					flag = "W";
				else
					System.err.println(new Desconhecido().getMessage());
			}
			else if(args[i].equals("-d")) {
				pathDocentes = args[i+1];
			}
			else if(args[i].equals("-v")) {
				pathVeiculos = args[i+1];
			}
			else if(args[i].equals("-p")) {
				pathPublicacoes = args[i+1];
			}
			else if(args[i].equals("-q")) {
				pathQualificacoes = args[i+1];
			}
			else if(args[i].equals("-r")) {
				pathRegras = args[i+1];
			}
			else if(args[i].equals("-a")) {
				try {
					anoRecredenciamento = Integer.parseInt(args[i+1].trim());
				} catch (NumberFormatException e) {
					System.err.println(new Desconhecido().getMessage());
				}
			}
		}
		if(flag == null) {
			try {
				sistema.carregaArquivoDocentes(pathDocentes);
			} catch (ErroDeIO | ErroDeFormatacao | CodigoRepetido e) {
				System.err.println(e.getMessage());
			}
			try {
				sistema.carregaArquivoVeiculos(pathVeiculos);
			} catch (ErroDeIO | VeiculoDesconhecido | CodigoRepetido | ErroDeFormatacao e) {
				System.err.println(e.getMessage());
			}
			try {
				sistema.carregaArquivoQualificacoes(pathQualificacoes);
			} catch (ErroDeIO | SiglaVeiculoNaoDefinida | QualiDesconhecidoVeiculo | ErroDeFormatacao e) {
				System.err.println(e.getMessage());
			}
			try {
				sistema.carregaArquivoPublicacoes(pathPublicacoes);
			} catch (ErroDeIO | VeiculoDesconhecido | Desconhecido | ErroDeFormatacao | CodNaoDefinido
					| CodigoRepetido e) {
				System.err.println(e.getMessage());
			}
			try {
				sistema.carregaArquivoPontuacoes(pathRegras);
			} catch (ErroDeIO | Desconhecido | ErroDeFormatacao | QualiDesconhecidoRegra e) {
				System.err.println(e.getMessage());
			}
			
			try {
				sistema.escreveArquivoRecredenciamento(anoRecredenciamento);
			} catch (ErroDeIO e) {
				System.err.println(e.getMessage());
			}
			try {
				sistema.escreveArquivoPublicacoes();
			} catch (ErroDeIO e) {
				System.err.println(e.getMessage());
			}
			try {
				sistema.escreveArquivoEstatisticas();
			} catch (ErroDeIO e) {
				System.err.println(e.getMessage());
			}
		}
		else if(flag.equals("R")) {
			try {
				sistema.carregaArquivoDocentes(pathDocentes);
			} catch (ErroDeIO | ErroDeFormatacao | CodigoRepetido e) {
				System.err.println(e.getMessage());
			}
			try {
				sistema.carregaArquivoVeiculos(pathVeiculos);
			} catch (ErroDeIO | VeiculoDesconhecido | CodigoRepetido | ErroDeFormatacao e) {
				System.err.println(e.getMessage());
			}
			try {
				sistema.carregaArquivoQualificacoes(pathQualificacoes);
			} catch (ErroDeIO | SiglaVeiculoNaoDefinida | QualiDesconhecidoVeiculo | ErroDeFormatacao e) {
				System.err.println(e.getMessage());
			}
			try {
				sistema.carregaArquivoPublicacoes(pathPublicacoes);
			} catch (ErroDeIO | VeiculoDesconhecido | Desconhecido | ErroDeFormatacao | CodNaoDefinido
					| CodigoRepetido e) {
				System.err.println(e.getMessage());
			}
			try {
				sistema.carregaArquivoPontuacoes(pathRegras);
			} catch (ErroDeIO | Desconhecido | ErroDeFormatacao | QualiDesconhecidoRegra e) {
				System.err.println(e.getMessage());
			}
			
			try {
				PPGI.serializar(sistema);
			} catch (ErroDeIO | Desconhecido e) {
				System.err.println(e.getMessage());
			}
		}
		else if(flag.equals("W")) {
			try {
				sistema = PPGI.desserializar();
			} catch (ErroDeIO | Desconhecido e) {
				System.err.println(e.getMessage());
			}
			
			try {
				sistema.escreveArquivoRecredenciamento(anoRecredenciamento);
			} catch (ErroDeIO e) {
				System.err.println(e.getMessage());
			}
			try {
				sistema.escreveArquivoPublicacoes();
			} catch (ErroDeIO e) {
				System.err.println(e.getMessage());
			}
			try {
				sistema.escreveArquivoEstatisticas();
			} catch (ErroDeIO e) {
				System.err.println(e.getMessage());
			}
		}
		else
			System.err.println(new Desconhecido().getMessage());

	}

}
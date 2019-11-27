package avaliacaoPPGI;

import exceptions.*;

public class AvaliacaoPPGI {

	public static void main(String[] args) {

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
		if(args[args.length-1].equals("--read-only")) {
			if(flag == null)
				flag = "R";
			else
				System.err.println(new Desconhecido().getMessage());
		}
		else if(args[args.length-1].equals("--write-only")) {
			if(flag == null)
				flag = "W";
			else
				System.err.println(new Desconhecido().getMessage());
		}

		if(flag == null) {
			try {
				sistema.carregaArquivoDocentes(pathDocentes);
				sistema.carregaArquivoVeiculos(pathVeiculos);
				sistema.carregaArquivoQualificacoes(pathQualificacoes);
				sistema.carregaArquivoPublicacoes(pathPublicacoes);
				sistema.carregaArquivoPontuacoes(pathRegras);
				
				sistema.escreveArquivoRecredenciamento(anoRecredenciamento);
				sistema.escreveArquivoPublicacoes();
				sistema.escreveArquivoEstatisticas();
			}
			catch (ErroDeIO | ErroDeFormatacao | CodigoRepetido | VeiculoDesconhecido | SiglaVeiculoNaoDefinida | QualiDesconhecidoVeiculo | Desconhecido | CodNaoDefinido | QualiDesconhecidoRegra e) {
				System.err.println(e.getMessage());
			}
		}
		
		else if(flag.equals("R")) {
			try {
				sistema.carregaArquivoDocentes(pathDocentes);
				sistema.carregaArquivoVeiculos(pathVeiculos);
				sistema.carregaArquivoQualificacoes(pathQualificacoes);
				sistema.carregaArquivoPublicacoes(pathPublicacoes);
				sistema.carregaArquivoPontuacoes(pathRegras);
				
				PPGI.serializar(sistema);
			}
			catch (ErroDeIO | ErroDeFormatacao | CodigoRepetido | VeiculoDesconhecido | Desconhecido | CodNaoDefinido | SiglaVeiculoNaoDefinida | QualiDesconhecidoVeiculo | QualiDesconhecidoRegra e) {
				System.err.println(e.getMessage());
			}

		}
		else if(flag.equals("W")) {
			try {
				sistema = PPGI.desserializar();
				
				sistema.escreveArquivoRecredenciamento(anoRecredenciamento);
				sistema.escreveArquivoPublicacoes();
				sistema.escreveArquivoEstatisticas();
			}
			catch (ErroDeIO | Desconhecido e) {
				System.err.println(e.getMessage());
			}

		}
		else
			System.err.println(new Desconhecido().getMessage());

	}

}
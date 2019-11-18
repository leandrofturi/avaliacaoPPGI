package avaliacaoPPGI;

import java.io.IOException;

import exceptions.*;
import serialize.Serialize;

public class AvaliacaoPPGI {

	public static void main(String[] args) throws IOException, ErroDeFormatacao, ErroDeIO, CodigoRepetido, VeiculoDesconhecido, SiglaVeiculoNaoDefinida, CodSiglaNaoDefinido, QualiDesconhecidoVeiculo, QualiDesconhecidoRegra, ClassNotFoundException, ClasseNaoEncontrada {
		
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
				else throw new ErroDeIO();
			}
			else if(args[i].equals("--write-only")) {
				if(flag == null)
					flag = "W";
				else throw new ErroDeIO();
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
					throw new ErroDeFormatacao();
				}
			}
		}
		if(flag == null) {
			sistema.carregaArquivoDocentes(pathDocentes);
			sistema.carregaArquivoVeiculos(pathVeiculos);
			sistema.carregaArquivoQualificacoes(pathQualificacoes);
			sistema.carregaArquivoPublicacoes(pathPublicacoes);
			sistema.carregaArquivoPontuacoes(pathRegras);
			
			sistema.escreveArquivoRecredenciamento(anoRecredenciamento);
			sistema.escreveArquivoPublicacoes();
			sistema.escreveArquivoEstatisticas();
		}
		else if(flag.equals("R")) {
			sistema.carregaArquivoDocentes(pathDocentes);
			sistema.carregaArquivoVeiculos(pathVeiculos);
			sistema.carregaArquivoQualificacoes(pathQualificacoes);
			sistema.carregaArquivoPublicacoes(pathPublicacoes);
			sistema.carregaArquivoPontuacoes(pathRegras);
			
			Serialize.serializar(sistema);
		}
		else if(flag.equals("W")) {
			sistema = (PPGI) Serialize.desserializar();
			
			sistema.escreveArquivoRecredenciamento(anoRecredenciamento);
			sistema.escreveArquivoPublicacoes();
			sistema.escreveArquivoEstatisticas();
		}
		else throw new ErroDeIO();

	}

}
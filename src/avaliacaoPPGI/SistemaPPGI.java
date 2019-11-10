package avaliacaoPPGI;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import utils.CSVmanager;

public class SistemaPPGI {
	
	private ArrayList<Docente> docentes = new ArrayList<Docente>();
	private Docente coordenador;
	private ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
	
	public Docente getDocente(Long codigo) {
		for(Docente aux : this.docentes) {
			if(aux.getCodigo().equals(codigo))
				return aux;
		}
		return null;
	}
	
	public Veiculo getVeiculo(String sigla) {
		for(Veiculo aux : this.veiculos) {
			if(aux.getSigla().equals(sigla))
				return aux;
		}
		return null;
	}
	
	public Docente getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Docente coordenador) {
		this.coordenador = coordenador;
	}

	public void carregaArquivoDocentes(String path) throws IOException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		CSVmanager reader = new CSVmanager();
		ArrayList<String[]> csv = reader.CSVread(path, ';', '.', true);
		
		for(String[] aux : csv) {
			
			Long codigo;
			String nome;
			Date dataNascimento;
			Date dataIngresso;
			
			try {
				codigo = Long.parseUnsignedLong(aux[0].trim());
			} catch (NumberFormatException e) {
				System.err.println("Erro de formatacao");
				return;
			}
			
			nome = aux[1].trim();
			
			try {
	            dataNascimento = formatter.parse(aux[2].trim());
	        } catch (ParseException e) {
	        	System.err.println("Erro de formatacao");
	        	return;
	        }
			
			try {
	            dataIngresso = formatter.parse(aux[2].trim());
	        } catch (ParseException e) {
	        	System.err.println("Erro de formatacao");
	        	return;
	        }
			
			Docente docente = new Docente(codigo, nome, dataNascimento, dataIngresso);
			this.docentes.add(docente);
			
			// PENSAR NESSE CASO
			if(aux[3].trim().equals("X"))
				coordenador = docente;
		}
	}
	
	public void carregaArquivoVeiculos(String path) throws IOException {
		CSVmanager reader = new CSVmanager();
		ArrayList<String[]> csv = reader.CSVread(path, ';', '.', true);

		for(String[] aux : csv) {
			
			String sigla;
			String nome;
			float fatorDeImpacto;
			
			sigla = aux[0].trim();
			
			nome = aux[1].trim();
			
			try {
				fatorDeImpacto = Float.parseFloat(aux[3].replace(',', '.').trim());
			} catch (NumberFormatException e) {
				System.err.println("Erro de formatacao");
				return;
			}
			
			if(aux[2].trim().equals("P")) {
				String ISSN = aux[4].trim();
				Periodico periodico = new Periodico(sigla, nome, fatorDeImpacto, ISSN);
				this.veiculos.add(periodico);
			}
			else if(aux[2].trim().equals("C")) {
				Conferencia conferencia = new Conferencia(sigla, nome, fatorDeImpacto);
				this.veiculos.add(conferencia);
			}
			else {
				System.err.println("Tipo de veiculo desconhecido para veiculo\n" + aux[0].trim() + ":" + aux[2].trim());
	        	return;
			}
		}
	}
	
	public void carregaArquivoPublicacoes(String path) throws IOException {
		CSVmanager reader = new CSVmanager();
		ArrayList<String[]> csv = reader.CSVread(path, ';', '.', true);
		
		for(String[] aux : csv) {
			
			int ano;
			String siglaVeiculo; Veiculo veiculo;
			String titulo;
			Long codAutor; Docente autor;
			int numero;
			int volume;
			String localConferencia;
			int paginaInicial;
			int paginaFinal;
			
			try {
				ano = Integer.parseInt(aux[0].trim());
			} catch (NumberFormatException e) {
				System.err.println("Erro de formatacao");
				return;
			}
			
			siglaVeiculo = aux[1].trim();
			veiculo = this.getVeiculo(siglaVeiculo);
			if(veiculo == null) {
				System.err.println("Sigla de veiculo nao definida usada na publicacao " + aux[2].trim() + ": " + aux[1].trim() + ".");
				return;
			}
			
			titulo = aux[2].trim();
			
			for(String autorAux : aux[3].split(",")) {
				try {
					codAutor = Long.parseUnsignedLong(autorAux.trim());
				} catch (NumberFormatException e) {
					System.err.println("Erro de formatacao");
					return;
				}
				autor = this.getDocente(codAutor);
				if(autor == null) {
					System.err.println("Código de docente não definido usado na publicação " + titulo + ": " + codAutor + ".");
					return;
				}
				//autores.add(autor);
			}
			
			try {
				numero = Integer.parseInt(aux[4].trim());
			} catch (NumberFormatException e) {
				System.err.println("Erro de formatacao");
				return;
			}
			
			try {
				volume = Integer.parseInt(aux[5].trim());
			} catch (NumberFormatException e) {
				System.err.println("Erro de formatacao");
				return;
			}
			
			localConferencia = aux[6].trim();
			
			try {
				paginaInicial = Integer.parseInt(aux[7].trim());
			} catch (NumberFormatException e) {
				System.err.println("Erro de formatacao");
				return;
			}
			
			try {
				paginaFinal = Integer.parseInt(aux[8].trim());
			} catch (NumberFormatException e) {
				System.err.println("Erro de formatacao");
				return;
			}
			
			// adicionar as publicacoes
		}
	}
	
	public void carregaArquivoQualificacoes(String path) throws IOException {
		CSVmanager reader = new CSVmanager();
		ArrayList<String[]> csv = reader.CSVread(path, ';', '.', true);

		for(String[] aux : csv) {
			
			int ano;
			String sigla;
			String qualis;
			
			try {
				ano = Integer.parseInt(aux[0].trim());
			} catch (NumberFormatException e) {
				System.err.println("Erro de formatacao");
				return;
			}
			
			sigla = aux[1].trim();
			
			qualis = aux[2].trim();
			
			Veiculo veiculo = this.getVeiculo(sigla);
			System.out.println(veiculo);
			if(veiculo == null) {
				System.err.println("Sigla de veiculo nao definida usada na qualificacao do ano " + ano + ": " + sigla + ".");
				return;
			}	
			veiculo.addQualis(ano, qualis);
		}
	}
	
	public void carregaArquivoPontuacoes(String path) throws IOException {
		CSVmanager reader = new CSVmanager();
		ArrayList<String[]> csv = reader.CSVread(path, ';', '.', true);

		for(String[] aux : csv) {
			
		}
	}
}

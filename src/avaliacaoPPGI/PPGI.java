package avaliacaoPPGI;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import exceptions.ErroDeFormatacao;
import exceptions.ErroDeIO;
import utils.CSVmanager;
import utils.PairList;

public class PPGI {
	
	private ArrayList<Docente> docentes = new ArrayList<Docente>();
	private Docente coordenador = null;
	private ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
	private ArrayList<Publicacao> publicacoes = new ArrayList<Publicacao>();
	private ArrayList<PontuadorPPGI> pontuadores = new ArrayList<PontuadorPPGI>();
	
	public Docente getDocente(Long codigo) {
		for(Docente aux : this.docentes) {
			if(aux.getCodigo().equals(codigo))
				return aux;
		}
		return null;
	}
	
	public void addDocente(Docente docente) {
		if(!this.docentes.contains(docente))
			this.docentes.add(docente);
	}
	
	public void imprimeDocentes() {
		for(Docente aux : this.docentes)
			System.out.println(aux);
	}
	
	public Docente getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Docente coordenador) {
		this.coordenador = coordenador;
	}
	
	public Veiculo getVeiculo(String sigla) {
		for(Veiculo aux : this.veiculos) {
			if(aux.getSigla().equals(sigla))
				return aux;
		}
		return null;
	}
	
	public void addVeiculo(Veiculo veiculo) {
		if(!this.veiculos.contains(veiculo))
			this.veiculos.add(veiculo);
	}
	
	public void imprimeVeiculos() {
		for(Veiculo aux : this.veiculos)
			System.out.println(aux);
	}
	
	public void addPublicacao(Publicacao publicacao) {
		if(!this.publicacoes.contains(publicacao))
			this.publicacoes.add(publicacao);
	}
	
	public void imprimePublicacoes() {
		for(Publicacao aux : this.publicacoes)
			System.out.println(aux);
	}

	public void carregaArquivoDocentes(String path) throws IOException, ErroDeFormatacao, ErroDeIO {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		for(String[] aux : CSVmanager.CSVread(path, ';', true)) {
			
			Long codigo;
			String nome;
			Date dataNascimento;
			Date dataIngresso;
			
			try {
				codigo = Long.parseUnsignedLong(aux[0].trim());
			} catch (NumberFormatException e) {
				throw new ErroDeFormatacao();
			}
			
			nome = aux[1].trim();
			
			try {
	            dataNascimento = formatter.parse(aux[2].trim());
	        } catch (ParseException e) {
	        	throw new ErroDeFormatacao();
	        }
			
			try {
	            dataIngresso = formatter.parse(aux[2].trim());
	        } catch (ParseException e) {
	        	throw new ErroDeFormatacao();
	        }
			
			Docente docente = new Docente(codigo, nome, dataNascimento, dataIngresso);
			if(this.docentes.contains(docente)) {
				System.out.println("Codigo repetido para docente: " + aux[0].trim());
			}
			this.addDocente(docente);
			
			if(aux[3].trim().equals("X")) {
				if(this.coordenador != null) {
					throw new ErroDeFormatacao();
				}
				this.coordenador = docente;
			}
		}
		Collections.sort(this.docentes);
	}
	
	public void carregaArquivoVeiculos(String path) throws IOException, ErroDeFormatacao, ErroDeIO {

		for(String[] aux : CSVmanager.CSVread(path, ';', true)) {
			
			String sigla;
			String nome;
			float fatorDeImpacto;
			
			sigla = aux[0].trim();
			
			nome = aux[1].trim();
			
			try {
				fatorDeImpacto = Float.parseFloat(aux[3].replace(',', '.').trim());
			} catch (NumberFormatException e) {
				throw new ErroDeFormatacao();
			}
			
			if(aux[2].trim().equals("P")) {
				String ISSN = aux[4].trim();
				Periodico periodico = new Periodico(sigla, nome, fatorDeImpacto, ISSN);
				this.addVeiculo(periodico);
			}
			else if(aux[2].trim().equals("C")) {
				Conferencia conferencia = new Conferencia(sigla, nome, fatorDeImpacto);
				this.addVeiculo(conferencia);
			}
			else {
				System.err.println("Tipo de veiculo desconhecido para veiculo\n" + aux[0].trim() + ":" + aux[2].trim());
	        	return;
			}
		}
	}
	
	public void carregaArquivoPublicacoes(String path) throws IOException, ErroDeFormatacao, ErroDeIO {
		
		for(String[] aux : CSVmanager.CSVread(path, ';', true)) {
			
			int ano;
			String siglaVeiculo; Veiculo veiculo;
			String titulo;
			Long codAutor; ArrayList<Docente> autores = new ArrayList<Docente>(); Docente autor;
			int numero;
			int volume;
			String localConferencia;
			int paginaInicial;
			int paginaFinal;
			
			try {
				ano = Integer.parseInt(aux[0].trim());
			} catch (NumberFormatException e) {
				throw new ErroDeFormatacao();
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
					throw new ErroDeFormatacao();
				}
				autor = this.getDocente(codAutor);
				if(autor == null) {
					System.err.println("Codigo de docente nao definido usado na publicacao " + titulo + ": " + codAutor + ".");
					return;
				}
				if(!autores.contains(autor))
					autores.add(autor);
			}
			
			try {
				numero = Integer.parseInt(aux[4].trim());
			} catch (NumberFormatException e) {
				throw new ErroDeFormatacao();
			}
			
			try {
				paginaInicial = Integer.parseInt(aux[7].trim());
			} catch (NumberFormatException e) {
				throw new ErroDeFormatacao();
			}
			
			try {
				paginaFinal = Integer.parseInt(aux[8].trim());
			} catch (NumberFormatException e) {
				throw new ErroDeFormatacao();
			}
			
			if(veiculo instanceof Periodico) {
				
				try {
					volume = Integer.parseInt(aux[5].trim());
				} catch (NumberFormatException e) {
					throw new ErroDeFormatacao();
				}
				
				Publicacao publicacao = new PublicacaoPeriodico(ano, veiculo, titulo, autores,
						numero, volume, paginaInicial, paginaFinal);
				this.addPublicacao(publicacao);
			}
			
			else if(veiculo instanceof Conferencia) {
				
				localConferencia = aux[6].trim();
				
				Publicacao publicacao = new PublicacaoConferencia(ano, veiculo, titulo, autores,
						numero, localConferencia, paginaInicial, paginaFinal);
				this.addPublicacao(publicacao);
			}
			
			else {
				System.err.println("Sigla de veiculo nao definida usada na publicacao " + aux[2].trim() + ": " + aux[1].trim() + ".");
				return;
			}
		}

	}
	
	public void carregaArquivoQualificacoes(String path) throws IOException, ErroDeFormatacao, ErroDeIO {
		
		for(String[] aux : CSVmanager.CSVread(path, ';', true)) {
			
			int ano;
			String sigla;
			String qualis;
			
			try {
				ano = Integer.parseInt(aux[0].trim());
			} catch (NumberFormatException e) {
				throw new ErroDeFormatacao();
			}
			
			sigla = aux[1].trim();
			
			qualis = aux[2].trim();
			
			Veiculo veiculo = this.getVeiculo(sigla);
			if(veiculo == null) {
				System.err.println("Sigla de veiculo nao definida usada na qualificacao do ano " + ano + ": " + sigla + ".");
				return;
			}	
			veiculo.addQualis(ano, qualis);
		}
	}
	
	public void carregaArquivoPontuacoes(String path) throws IOException, ErroDeIO, ErroDeFormatacao {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		for(String[] aux : CSVmanager.CSVread(path, ';', true)) {
			
			Date dataInicio;
			Date dataFim;
			PairList<String, Integer> qualis = new PairList<String, Integer>(); String[] quali; String[] pontos;
			float multiplicador;
			int qtdAnosAConsiderar;
			int pontuacaoMinRecredenciamento;
			
			try {
				dataInicio = formatter.parse(aux[0].trim());
	        } catch (ParseException e) {
	        	throw new ErroDeFormatacao();
	        }
			
			try {
				dataFim = formatter.parse(aux[1].trim());
	        } catch (ParseException e) {
	        	throw new ErroDeFormatacao();
	        }
			
			quali = aux[2].trim().split(",");
			pontos = aux[3].trim().split(",");
			if(quali.length != pontos.length) {
				throw new ErroDeFormatacao();
			}
			for(int i = 0; i < quali.length; i++) {
				String qual;
				int ponto;
				
				qual = quali[i].trim();
				try {
					ponto = Integer.parseInt(aux[5].trim());
				} catch (NumberFormatException e) {
					throw new ErroDeFormatacao();
				}
				qualis.put(qual, ponto);
			}
			
			try {
				multiplicador = Float.parseFloat(aux[4].replace(',', '.').trim());
			} catch (NumberFormatException e) {
				throw new ErroDeFormatacao();
			}
			
			try {
				qtdAnosAConsiderar = Integer.parseInt(aux[5].trim());
			} catch (NumberFormatException e) {
				throw new ErroDeFormatacao();
			}
			
			try {
				pontuacaoMinRecredenciamento = Integer.parseInt(aux[6].trim());
			} catch (NumberFormatException e) {
				throw new ErroDeFormatacao();
			}
			
			PontuadorPPGI pontuador = new PontuadorPPGI(dataInicio, dataFim, qualis, multiplicador, qtdAnosAConsiderar, pontuacaoMinRecredenciamento);
			
			if(this.pontuadores.contains(pontuador)) {
				throw new ErroDeFormatacao();
			}
			this.pontuadores.add(pontuador);
		}
	}
	
	public void escreveArquivoRecredenciamento(String path) throws ErroDeIO, IOException {
		
		ArrayList<String[]> content = new ArrayList<String[]>();
		Collections.sort(this.docentes);
		
		for(Docente docente : this.docentes) {
			String[] line = new String[3];
			line[0] = docente.getNome();
			
			line[1] = "Pontuacao alcancada";
			
			if(docente.equals(this.coordenador))
				line[2] = "Coordenador";
			else if(docente.getTempoDeIngresso() <= 3)
				line[2] = "PPJ";
			else if(docente.getIdade() > 60)
				line[2] = "PPS";
			//else if docente não se enquadra nos casos acima e alcançou a pontuação mínima
			else
				line[2] = "Nao";
			content.add(line);
		}
		
		CSVmanager.CSVwriter(content, "recredenciamento.csv", ';', ',');
	}
	
}

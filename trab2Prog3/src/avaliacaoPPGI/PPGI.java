package avaliacaoPPGI;

import java.io.IOException;
import java.io.Serializable;
//import java.text.Collator;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

import exceptions.*;
import serialize.Serialize;
import utils.CSVmanager;
import utils.PairList;

public class PPGI implements Serializable {
	
	private ArrayList<Docente> docentes = new ArrayList<Docente>();
	private Docente coordenador = null;
	private ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
	private ArrayList<Publicacao> publicacoes = new ArrayList<Publicacao>();
	private ArrayList<PontuadorPPGI> pontuadores = new ArrayList<PontuadorPPGI>();
	
	private static final long serialVersionUID = 1L;

	/*
	public PPGI() {
		super();
		System.out.println("─▄▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▄");
		System.out.println("█░░░█░░░░░░░░░░▄▄░██░█");
		System.out.println("█░▀▀█▀▀░▄▀░▄▀░░▀▀░▄▄░█");
		System.out.println("█░░░▀░░░▄▄▄▄▄░░██░▀▀░█");
		System.out.println("─▀▄▄▄▄▄▀─────▀▄▄▄▄▄▄▀");
		System.out.println("Bem-vindo ao sistema do PPGI!");
		System.out.println("Aqui você pode carregar arquivos específicos e gerar arquivos mais específicos ainda." + '\n');
	}
	*/

	private Docente getDocente(Long codigo) {
		for(Docente aux : this.docentes) {
			if(aux.getCodigo().equals(codigo))
				return aux;
		}
		return null;
	}
	
	public void imprimeDocentes() {
		for(Docente aux : this.docentes)
			System.out.println(aux);
	}
	
	public void imprimeCoordenador() {
		System.out.println(this.coordenador);
	}
	
	private Veiculo getVeiculo(String sigla) {
		for(Veiculo aux : this.veiculos) {
			if(aux.getSigla().equals(sigla))
				return aux;
		}
		return null;
	}
	
	public void imprimeVeiculos() {
		for(Veiculo aux : this.veiculos)
			System.out.println(aux);
	}
	
	public void imprimePublicacoes() {
		for(Publicacao aux : this.publicacoes)
			System.out.println(aux);
	}
	
	private ArrayList<Publicacao> publicacoesPorDocente(Docente docente) {

		ArrayList<Publicacao> publicacoesDocente = new ArrayList<Publicacao>();
		for(Publicacao aux : this.publicacoes) {
			if(aux.isAutor(docente))
				publicacoesDocente.add(aux);
		}
		return publicacoesDocente;
	}
	
	private ArrayList<Publicacao> publicacoesPorQualis(String qualis) {
		
		ArrayList<Publicacao> publicacoesQualis = new ArrayList<Publicacao>();
		for(Publicacao aux : this.publicacoes) {
			if(aux.getQualis().equals(qualis))
				publicacoesQualis.add(aux);
		}
		return publicacoesQualis;
	}
	
	private PontuadorPPGI getPontuador(int ano) {
		
		PontuadorPPGI pontuador = this.pontuadores.get(0);
		for(PontuadorPPGI aux : this.pontuadores) {
			if(ChronoUnit.DAYS.between((aux.getDataInicio()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
					LocalDate.of(ano, 1, 1)) < 0)
				break;
			else
				pontuador = aux;
		}
		return pontuador;
		
	}
	
	private float calculaPontuacao(int anoRecredenciamento, Docente docente) {

		PontuadorPPGI pontuador = this.getPontuador(anoRecredenciamento);
		float pontuacaoFinal = (float) 0.0;

		for(Publicacao aux : this.publicacoesPorDocente(docente)) {
			
			if((anoRecredenciamento-aux.getAno() <= pontuador.getQtdAnosAConsiderar()) && (anoRecredenciamento > aux.getAno())) {

				if(aux instanceof PublicacaoPeriodico)
					pontuacaoFinal += pontuador.getMultiplicador()*pontuador.getPontuacao(aux.getQualis());
				else if(aux instanceof PublicacaoConferencia)
					pontuacaoFinal += pontuador.getPontuacao(aux.getQualis());
			}
		}
		
		return pontuacaoFinal;
	}

	public void imprimePontuadores() {
		for(PontuadorPPGI aux : this.pontuadores)
			System.out.println(aux);
	}
	
	public void carregaArquivoDocentes(String path) throws ErroDeIO, ErroDeFormatacao, CodigoRepetido {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			for(String[] aux : CSVmanager.CSVread(path, ';', true)) {
				
				Long codigo = null;
				String nome = null;
				Date dataNascimento = null;
				Date dataIngresso = null;
				
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
			        dataIngresso = formatter.parse(aux[3].trim());
			    } catch (ParseException e) {
			    	throw new ErroDeFormatacao();
			    }
				
				Docente docente = new Docente(codigo, nome, dataNascimento, dataIngresso);
				if(this.docentes.contains(docente)) {
					throw new CodigoRepetido("docente", aux[0].trim());
				}
				this.docentes.add(docente);
				
				if((aux[4].trim().equals("X")) || (aux[4].trim().equals("x"))) {
					if(this.coordenador != null) {
						throw new CodigoRepetido("docente", aux[0].trim());
					}
					this.coordenador = docente;
				}
			}
		} catch (IOException e) {
			throw new ErroDeIO();
		}
		//System.out.printf(path + " carregado!" + '\n');
	}
	
	public void carregaArquivoVeiculos(String path) throws ErroDeIO, VeiculoDesconhecido, CodigoRepetido, ErroDeFormatacao {

		try {
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
					if(this.veiculos.contains(periodico)) {
						throw new CodigoRepetido("veículo", aux[0].trim());
					}
					this.veiculos.add(periodico);
				}
				else if(aux[2].trim().equals("C")) {
					Conferencia conferencia = new Conferencia(sigla, nome, fatorDeImpacto);
					if(this.veiculos.contains(conferencia)) {
						throw new CodigoRepetido("veículo", aux[0].trim());
					}
					this.veiculos.add(conferencia);
				}
				else {
					throw new VeiculoDesconhecido(aux[2].trim(), aux[0].trim());
				}
			}
		} catch (IOException e) {
			throw new ErroDeIO();
		}
		//System.out.printf(path + " carregado!" + '\n');
	}
	
	public void carregaArquivoPublicacoes(String path) throws ErroDeIO, VeiculoDesconhecido, Desconhecido, ErroDeFormatacao, CodNaoDefinido, CodigoRepetido {
		
		try {
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
					throw new CodNaoDefinido("veículo", aux[2].trim(), aux[1].trim());
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
						throw new CodNaoDefinido("docente", aux[2].trim(), autorAux);
					}
					if(autores.contains(autor)) {
						throw new CodigoRepetido("docente", autorAux);
					}
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
					if(this.publicacoes.contains(publicacao)) {
						throw new Desconhecido();
					}
					this.publicacoes.add(publicacao);
				}
				
				else if(veiculo instanceof Conferencia) {
					
					localConferencia = aux[6].trim();
					
					Publicacao publicacao = new PublicacaoConferencia(ano, veiculo, titulo, autores,
							numero, localConferencia, paginaInicial, paginaFinal);
					if(this.publicacoes.contains(publicacao)) {
						throw new Desconhecido();
					}
					this.publicacoes.add(publicacao);
				}
				
				else {
					throw new VeiculoDesconhecido(veiculo.getClass().toString(), aux[1].trim());
				}
			}
		} catch (IOException e) {
			throw new ErroDeIO();
		}
		//System.out.printf(path + " carregado!" + '\n');
	}
	
	public void carregaArquivoQualificacoes(String path) throws ErroDeIO, SiglaVeiculoNaoDefinida, QualiDesconhecidoVeiculo, ErroDeFormatacao {
		
		try {
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
				if(!PontuadorPPGI.containsQualis(qualis)) {
					throw new QualiDesconhecidoVeiculo(aux[2].trim(), aux[1].trim(), aux[0].trim());
				}
				
				Veiculo veiculo = this.getVeiculo(sigla);
				if(veiculo == null) {
					throw new SiglaVeiculoNaoDefinida(aux[0].trim(), aux[1].trim());
				}	
				veiculo.addQualis(ano, qualis);
			}
		} catch (IOException e) {
			throw new ErroDeIO();
		}
		//System.out.printf(path + " carregado!" + '\n');
	}
	
	public void carregaArquivoPontuacoes(String path) throws ErroDeIO, Desconhecido, ErroDeFormatacao, QualiDesconhecidoRegra {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
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
					if(!PontuadorPPGI.containsQualis(qual)) {
						throw new QualiDesconhecidoRegra(quali[i].trim(), aux[0].trim());
					}
						
					try {
						ponto = Integer.parseInt(pontos[i].trim());
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
					throw new Desconhecido();
				}
				this.pontuadores.add(pontuador);
			}
		} catch (IOException e) {
			throw new ErroDeIO();
		}
		//System.out.printf(path + " carregado!" + '\n');
	}
	
	public void escreveArquivoRecredenciamento(int ano) throws ErroDeIO {

		ArrayList<String[]> content = new ArrayList<String[]>();
		content.add(new String[] {"Docente", "Pontuação", "Recredenciado?"});
		DecimalFormat formater = new DecimalFormat("0.0");
		//Collator collator = Collator.getInstance();
		//collator.setStrength(Collator.CANONICAL_DECOMPOSITION);
		
		Collections.sort(this.docentes, new Comparator<Docente>() {
			@Override
		    public int compare(Docente d1, Docente d2) {
		        //return collator.compare(d1.getNome(), d2.getNome()));
				return d1.getNome().compareTo(d2.getNome());
		    }
		});
		
		for(Docente docente : this.docentes) {
			String[] line = new String[3];
			line[0] = docente.getNome();
			
			float pontuacao = calculaPontuacao(ano, docente);
			line[1] = formater.format(pontuacao);

			if(docente.equals(this.coordenador))
				line[2] = "Coordenador";
			else if(docente.getTempoDeIngresso(ano) <= 3)
				line[2] = "PPJ";
			else if(docente.getIdade(ano) > 60)
				line[2] = "PPS";
			else if(pontuacao >= this.getPontuador(ano).getPontuacaoMinRecredenciamento())
				line[2] = "Sim";
			else
				line[2] = "Não";
			content.add(line);
		}
		
		try {
			CSVmanager.CSVwriter(content, "1-recredenciamento.csv", ';', ',');
		} catch (IOException e) {
			throw new ErroDeIO();
		}
		//System.out.printf("Recredenciamento foi salvo em data/1-recredenciamento.csv" + '\n');
	}
	
	public void escreveArquivoPublicacoes() throws ErroDeIO {
		
		ArrayList<String[]> content = new ArrayList<String[]>();
		content.add(new String[] {"Ano","Sigla Veículo","Veículo","Qualis","Fator de Impacto","Título","Docentes"});
		DecimalFormat formater = new DecimalFormat("0.000");
		
		Collections.sort(this.publicacoes, new Comparator<Publicacao>() {
			@Override
		    public int compare(Publicacao p1, Publicacao p2) {
		        int alphaCompare;
		        alphaCompare = p1.getQualis().compareTo(p2.getQualis());
		        if(alphaCompare == 0) {
		        	alphaCompare = (-1) * ((Integer) p1.getAno()).compareTo((Integer) p2.getAno());
		        	if(alphaCompare == 0) {
		        		alphaCompare = p1.getVeiculo().getSigla().compareTo(p2.getVeiculo().getSigla());
		        		if(alphaCompare == 0) {
		        			alphaCompare = p1.getTitulo().compareTo(p2.getTitulo());
		        		}
		        	}
		        }
		        return alphaCompare;
		    }
		});
		
		for(Publicacao aux : this.publicacoes) {
			String[] line = new String[7];
			
			line[0] = Objects.toString(aux.getAno());
			
			line[1] = aux.getVeiculo().getSigla();
			
			line[2] = aux.getVeiculo().getNome();
			
			line[3] = aux.getQualis();
			
			line[4] = formater.format(aux.getVeiculo().getFatorDeImpacto());
			
			line[5] = aux.getTitulo();
			
			line[6] = "";
			for(Docente auxAutor : aux.getAutores())
				line[6] += auxAutor.getNome() + ",";
			line[6] = line[6].substring(0, line[6].length()-1);
			
			content.add(line);
		}
		
		try {
			CSVmanager.CSVwriter(content, "2-publicacoes.csv", ';', ',');
		} catch (IOException e) {
			throw new ErroDeIO();
		}
		//System.out.printf("Publicacoes foram salvas em data/2-publicacoes.csv" + '\n');
	}
	
	public void escreveArquivoEstatisticas() throws ErroDeIO {
		ArrayList<String[]> content = new ArrayList<String[]>();
		content.add(new String[] {"Qualis","Qtd. Artigos","Média Artigos / Docente"});
		DecimalFormat formater = new DecimalFormat("0.00");
		
		for(String qualis : PontuadorPPGI.qualisRef) {
			String[] line = new String[3];
			
			line[0] = qualis;
			
			ArrayList<Publicacao> publicacoesQualis = this.publicacoesPorQualis(qualis);
			line[1] = Objects.toString(publicacoesQualis.size());
			
			double mediaAutores = 0.0;
			for(Publicacao aux : publicacoesQualis)
				mediaAutores += 1.0/(aux.getAutores().size());
			line[2] = formater.format(mediaAutores);
			
			content.add(line);
		}
		
		try {
			CSVmanager.CSVwriter(content, "3-estatisticas.csv", ';', ',');
		} catch (IOException e) {
			throw new ErroDeIO();
		}
		//System.out.printf("Estatisticas foram salvas em data/3-estatisticas.csv" + '\n');
	}
	
	public static void serializar(PPGI sistema) throws ErroDeIO, Desconhecido {
		Serialize.serializar("recredenciamento.dat", sistema);
	}
	
	public static PPGI desserializar() throws ErroDeIO, Desconhecido {
		PPGI sistema = (PPGI) Serialize.desserializar("recredenciamento.dat");
		return sistema;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PPGI other = (PPGI) obj;
		if (coordenador == null) {
			if (other.coordenador != null)
				return false;
		} else if (!coordenador.equals(other.coordenador))
			return false;
		if (docentes == null) {
			if (other.docentes != null)
				return false;
		} else if (!docentes.equals(other.docentes))
			return false;
		if (pontuadores == null) {
			if (other.pontuadores != null)
				return false;
		} else if (!pontuadores.equals(other.pontuadores))
			return false;
		if (publicacoes == null) {
			if (other.publicacoes != null)
				return false;
		} else if (!publicacoes.equals(other.publicacoes))
			return false;
		if (veiculos == null) {
			if (other.veiculos != null)
				return false;
		} else if (!veiculos.equals(other.veiculos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PPGI [docentes=" + docentes + ", coordenador=" + coordenador + ", veiculos=" + veiculos
				+ ", publicacoes=" + publicacoes + ", pontuadores=" + pontuadores + "]";
	}
	
}
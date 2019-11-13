package avaliacaoPPGI;

import java.text.SimpleDateFormat;
import java.util.Date;

import utils.PairList;

public class PontuadorPPGI extends PPGI {

	private final Date dataInicio;
	private final Date dataFim;
	private final PairList<String, Integer> qualis;
	private final double multiplicador;
	private final int qtdAnosAConsiderar;
	private final int pontuacaoMinRecredenciamento;
	
	public PontuadorPPGI(Date dataInicio, Date dataFim, double multiplicador, int qtdAnosAConsiderar, int pontuacaoMinRecredenciamento) {
		super();
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		qualis = new PairList<String, Integer>();
		this.multiplicador = multiplicador;
		this.qtdAnosAConsiderar = qtdAnosAConsiderar;
		this.pontuacaoMinRecredenciamento = pontuacaoMinRecredenciamento;
	}
	
	public void addQualis(String qualis, int pontos) {
		if(!this.qualis.contains(qualis, pontos))
			this.qualis.put(qualis, pontos);
	}
	
	public PontuadorPPGI(Date dataInicio, Date dataFim, PairList<String, Integer> qualis, double multiplicador, int qtdAnosAConsiderar, int pontuacaoMinRecredenciamento) {
		super();
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.qualis = qualis;
		this.multiplicador = multiplicador;
		this.qtdAnosAConsiderar = qtdAnosAConsiderar;
		this.pontuacaoMinRecredenciamento = pontuacaoMinRecredenciamento;
	}

	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return "PontuadorPPGI [dataInicio=" + formatter.format(dataInicio) + ", dataFim=" + formatter.format(dataFim) + ", qualis=" + qualis
				+ ", multiplicador=" + multiplicador + ", qtdAnosAConsiderar=" + qtdAnosAConsiderar
				+ ", pontuacaoMinRecredenciamento=" + pontuacaoMinRecredenciamento + "]";
	}
	
	public int calculaPontuacao(Docente docente) {
		
		return pontuacaoMinRecredenciamento;
	}
	
}

package avaliacaoPPGI;

import java.text.SimpleDateFormat;
import java.util.Date;

import utils.PairList;

public class PontuadorPPGI {

	private Date dataInicio;
	private Date dataFim;
	private PairList<String, Integer> qualis;
	private double multiplicador;
	private int qtdAnosAConsiderar;
	private int pontuacaoMinRecredenciamento;
	
	public final static String[] qualisRef = {"A1","A2","B1","B2","B3","B4","B5","C"};
	
	public static boolean containsQualis(String qualis) {
		for(String aux : PontuadorPPGI.qualisRef)
			if(aux.equals(qualis)) return true;
		return false;
	}
	
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
	
	public Date getDataInicio() {
		return dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public int getPontuacaoMinRecredenciamento() {
		return pontuacaoMinRecredenciamento;
	}

	public int getPontuacao(String quali) {

		int pontuacao = 0;
		for(String aux : PontuadorPPGI.qualisRef) {
			if(this.qualis.findByFirst(aux) != null)
				pontuacao = this.qualis.findByFirst(aux).getSecond();

			if(aux.equals(quali))
				break;
		}

		return pontuacao;
	}

	public double getMultiplicador() {
		return multiplicador;
	}

	public int getQtdAnosAConsiderar() {
		return qtdAnosAConsiderar;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PontuadorPPGI other = (PontuadorPPGI) obj;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (Double.doubleToLongBits(multiplicador) != Double.doubleToLongBits(other.multiplicador))
			return false;
		if (pontuacaoMinRecredenciamento != other.pontuacaoMinRecredenciamento)
			return false;
		if (qtdAnosAConsiderar != other.qtdAnosAConsiderar)
			return false;
		if (qualis == null) {
			if (other.qualis != null)
				return false;
		} else if (!qualis.equals(other.qualis))
			return false;
		return true;
	}

	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return "PontuadorPPGI [dataInicio=" + formatter.format(dataInicio) + ", dataFim=" + formatter.format(dataFim) + ", qualis=" + qualis
				+ ", multiplicador=" + multiplicador + ", qtdAnosAConsiderar=" + qtdAnosAConsiderar
				+ ", pontuacaoMinRecredenciamento=" + pontuacaoMinRecredenciamento + "]";
	}
	
}

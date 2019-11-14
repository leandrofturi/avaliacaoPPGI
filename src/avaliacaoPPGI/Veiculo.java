package avaliacaoPPGI;

import utils.Pair;
import utils.PairList;

abstract class Veiculo {
	
	private String sigla;
	private String nome;
	private float fatorDeImpacto;
	private PairList<Integer, String> qualis = new PairList<Integer, String>();
	
	public Veiculo(String sigla, String nome, float fatorDeImpacto) {
		super();
		this.sigla = sigla;
		this.nome = nome;
		this.fatorDeImpacto = fatorDeImpacto;
	}

	public String getSigla() {
		return sigla;
	}
	
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public float getFatorDeImpacto() {
		return fatorDeImpacto;
	}
	
	public void setFatorDeImpacto(float fatorDeImpacto) {
		this.fatorDeImpacto = fatorDeImpacto;
	}
	
	public String getQualis(int ano) {
		
		for(Pair<Integer, String> aux : this.qualis) {
			if(aux.getFirst() < ano)
				return aux.getSecond();
		}
		return this.qualis.begin().getSecond();
	}
	
	public void addQualis(int ano, String qualis) {
		if(!this.qualis.contains(ano, qualis))
			this.qualis.put(ano, qualis);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veiculo other = (Veiculo) obj;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}
	
}

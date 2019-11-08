package avaliacaoPPGI;

class Veiculo {
	
	private String sigla;
	private String nome;
	private Character tipo;
	private float fatorDeImpacto;
	private String ISSN;
	
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
	
	public Character getTipo() {
		return tipo;
	}
	
	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}
	
	public float getFatorDeImpacto() {
		return fatorDeImpacto;
	}
	
	public void setFatorDeImpacto(float fatorDeImpacto) {
		this.fatorDeImpacto = fatorDeImpacto;
	}
	
	public String getISSN() {
		return ISSN;
	}
	
	public void setISSN(String iSSN) {
		ISSN = iSSN;
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
		if (ISSN == null) {
			if (other.ISSN != null)
				return false;
		} else if (!ISSN.equals(other.ISSN))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Veiculo [sigla=" + sigla + ", nome=" + nome + ", tipo=" + tipo + ", fatorDeImpacto=" + fatorDeImpacto
				+ ", ISSN=" + ISSN + "]";
	}
}

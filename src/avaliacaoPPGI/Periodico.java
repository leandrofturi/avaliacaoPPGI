package avaliacaoPPGI;

class Periodico extends Veiculo {

	private String ISSN;
	
	private static final long serialVersionUID = 1L;
	
	public Periodico(String sigla, String nome, float fatorDeImpacto, String iSSN) {
		super(sigla, nome, fatorDeImpacto);
		this.ISSN = iSSN;
	}
	
	public String getISSN() {
		return ISSN;
	}
	
	public void setISSN(String iSSN) {
		ISSN = iSSN;
	}

	@Override
	public String toString() {
		return "Periodico [sigla=" + getSigla() + ", nome=" + getNome()
		+ ", fatorDeImpacto=" + getFatorDeImpacto() + ", ISSN=" + ISSN + "]";
	}

}

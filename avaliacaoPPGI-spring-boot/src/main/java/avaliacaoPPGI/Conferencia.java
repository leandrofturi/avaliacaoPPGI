package avaliacaoPPGI;

class Conferencia extends Veiculo {

	public Conferencia(String sigla, String nome, float fatorDeImpacto) {
		super(sigla, nome, fatorDeImpacto);
	}

	@Override
	public String toString() {
		return "Conferencia [sigla=" + getSigla() + ", nome=" + getNome()
				+ ", fatorDeImpacto=" + getFatorDeImpacto() + "]";
	}

}

package exceptions;

public class SiglaVeiculoNaoDefinida extends Exception {
	
	String ano = null;
	String sigla;
	
	private static final long serialVersionUID = 1L;

	public SiglaVeiculoNaoDefinida(String ano, String sigla) {
		super();
		this.ano = ano;
		this.sigla = sigla;
	}

	@Override
	public String getMessage() {
		if(this.ano != null)
			return "Sigla de veículo não definida usada na qualificação do ano \"" + this.ano + "\": " + this.sigla + ".";
		
		return "Sigla de veículo não definida.";
	}
}

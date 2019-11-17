package exceptions;

public class QualiDesconhecidoVeiculo extends Exception {
	
	private String qualis = null;
	private String sigla;
	private String ano;

	public QualiDesconhecidoVeiculo(String qualis, String sigla, String ano) {
		super();
		this.qualis = qualis;
		this.sigla = sigla;
		this.ano = ano;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		if(this.qualis != null)
			return "Qualis desconhecido para qualificação do veículo " + this.sigla + " no ano " + this.ano + ": " + this.qualis;
		
		return "Qualis desconhecido";
	}
}

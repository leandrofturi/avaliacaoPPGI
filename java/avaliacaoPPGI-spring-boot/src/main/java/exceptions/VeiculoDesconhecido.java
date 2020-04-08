package exceptions;

public class VeiculoDesconhecido extends Exception {
	
	private String tipo = null;
	private String sigla;
	
	private static final long serialVersionUID = 1L;

	public VeiculoDesconhecido(String tipo, String sigla) {
		super();
		this.tipo = tipo;
		this.sigla = sigla;
	}

	@Override
	public String getMessage() {
		if(tipo != null)
			return "Tipo de veículo desconhecido para veículo " + this.sigla + ": " + this.tipo + ".";
		
		return "Tipo de veículo desconhecido.";
	}
}

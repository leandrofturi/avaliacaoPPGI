package exceptions;

public class QualiDesconhecidoRegra extends Exception {
	
	private String qualis = null;
	private String data;
	
	private static final long serialVersionUID = 1L;

	public QualiDesconhecidoRegra(String qualis, String data) {
		super();
		this.qualis = qualis;
		this.data = data;
	}

	@Override
	public String getMessage() {
		if(this.qualis != null)
			return "Qualis desconhecido para regras de " + this.data + ": " + this.qualis + ".";
		
		return "Qualis desconhecido.";
	}
}

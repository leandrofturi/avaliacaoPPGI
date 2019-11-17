package exceptions;

public class CodigoRepetido extends Exception {
	
	private String objeto = null;
	private String codigo;

	public CodigoRepetido(String objeto, String codigo) {
		super();
		this.objeto = objeto;
		this.codigo = codigo;
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		if(this.objeto.equals("docente"))
			return "Código repetido para docente: " + this.codigo;
		
		if(this.objeto.equals("veículo") || this.objeto.equals("veiculo"))
			return "Código repetido para veículo: " + this.codigo;
		
		return "Código repetido";
	}

}

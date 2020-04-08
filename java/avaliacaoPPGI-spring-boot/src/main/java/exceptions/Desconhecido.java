package exceptions;

public class Desconhecido extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {		
		return "Exceção não definida.";
	}

}

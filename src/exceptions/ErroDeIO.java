package exceptions;

public class ErroDeIO extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Erro de I/O.";
	}
}

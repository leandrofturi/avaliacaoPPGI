package exceptions;

public class ErroDeFormatacao extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Erro de formatação";
	}
}

package exceptions;

public class ClasseNaoEncontrada extends Exception {

	String classe = null;
	
	private static final long serialVersionUID = 1L;

	public ClasseNaoEncontrada(String classe) {
		super();
		this.classe = classe;
	}
	
	@Override
	public String getMessage() {
		if(this.classe != null)
			return this.classe + "classe não encontrada";
		
		return "Classe não encontrada";
	}
	
}

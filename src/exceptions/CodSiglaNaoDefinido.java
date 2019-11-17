package exceptions;

public class CodSiglaNaoDefinido extends Exception {
	
	private String objeto = null;
	private String titulo;
	private String sigla;

	public CodSiglaNaoDefinido(String objeto, String titulo, String sigla) {
		super();
		this.objeto = objeto;
		this.titulo = titulo;
		this.sigla = sigla;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {		
		if(this.objeto.equals("veículo") || this.objeto.equals("veiculo"))
			return "Sigla de veículo não definida usada na publicação \"" + this.titulo + "\": " + this.sigla;
		
		if(this.objeto.equals("docente"))
			return "Código de docente não definido usado na publicação \"" + this.titulo + "\": " + this.sigla;
			
		return "Sigla não definida";
	}
}

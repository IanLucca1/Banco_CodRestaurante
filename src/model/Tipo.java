package model;

public class Tipo extends Codigo {
	//ATRIBUTOS
	private String tipo;
	
	//CONSTRUTOR
    public Tipo() {
		super();
	}
    
    //GETTERS E SETTERS
    public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return tipo;
	}
	
	
}

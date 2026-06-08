package model;

public class Item extends Codigo {
	//ATRIBUTOS
    private String item;
    private double preco;
    private int codTipo;
	
    //CONSTRUTOR
    public Item(String item, double preco, int codTipo) {
		super();
		this.item = item;
		this.preco = preco;
		this.codTipo = codTipo;
	}
    
    public Item(int codigo, String item, double preco, int codTipo) {
		super();
		setCodigo(codigo);
		this.item = item;
		this.preco = preco;
		this.codTipo = codTipo;
	}

    //GETTERS E SETTERS
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getCodTipo() {
		return codTipo;
	}

	public void setCodTipo(int codTipo) {
		this.codTipo = codTipo;
	}

	@Override
	public String toString() {
		return this.item;
	}	
	
	
}

package model;

public class Item {
	
	private int codItem;
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

    //GETTERS E SETTERS
	public int getCodItem() {
		return codItem;
	}

	public void setCodItem(int codItem) {
		this.codItem = codItem;
	}

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
    
	
}

package model;

public class Pedido implements Faturavel {
	// ATRIBUTOS
	private int codConta;
	private int codItem;
	private int qtd;
	private double valor;

	// CONSTRUTORES
	public Pedido(int codConta, int codItem, int qtd, double valor) {
		super();
		this.codConta = codConta;
		this.codItem = codItem;
		this.qtd = qtd;
		this.valor = valor;
	}

	public Pedido() {
		super();
	}

	// GETTERS E SETTERS
	public int getCodConta() {
		return codConta;
	}

	public void setCodConta(int codConta) {
		this.codConta = codConta;
	}

	public int getCodItem() {
		return codItem;
	}

	public void setCodItem(int codItem) {
		this.codItem = codItem;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	//MÉTODOS
	@Override
	public void calcularValor(double precoItem) {
		this.valor = precoItem * this.qtd;
	}
}

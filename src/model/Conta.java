package model;

public class Conta extends Codigo implements Faturavel {
	// ATRIBUTOS
	private int codMesa;
	private double total;
	private char pago;

	// CONSTRUTOR
	public Conta(int codMesa, double total) {
		super();
		this.codMesa = codMesa;
		this.total = total;
	}
	
	public Conta() {
		super();
	}

	// GETTERS E SETTERS
	public int getCodMesa() {
		return codMesa;
	}

	public void setCodMesa(int codMesa) {
		this.codMesa = codMesa;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public char getPago() {
		return pago;
	}

	public void setPago(char pago) {
		this.pago = pago;
	}

	// MÉTODOS
	@Override
	public void calcularValor(double valorPedido) {
		this.total += valorPedido;
	}
}

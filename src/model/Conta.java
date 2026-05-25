package model;

public class Conta extends Codigo {
	 
	private int codComanda;
	 private float total;
	 private char pago;
	
	 public Conta(int codComanda, float total, char pago) {
		super();
		this.codComanda = codComanda;
		this.total = total;
		this.pago = pago;
	}

	public int getCodComanda() {
		return codComanda;
	}

	public void setCodComanda(int codComanda) {
		this.codComanda = codComanda;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public char getPago() {
		return pago;
	}

	public void setPago(char pago) {
		this.pago = pago;
	}
	 
}

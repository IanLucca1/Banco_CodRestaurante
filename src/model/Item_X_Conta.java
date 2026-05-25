package model;

public class Item_X_Conta {

	private int codConta;
	private int codItem;
	private int qtd;
	private char pago;
	
	public Item_X_Conta(int codConta, int codItem, int qtd, char pago) {
		super();
		this.codConta = codConta;
		this.codItem = codItem;
		this.qtd = qtd;
		this.pago = pago;
	}

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

	public char getPago() {
		return pago;
	}

	public void setPago(char pago) {
		this.pago = pago;
	}
}

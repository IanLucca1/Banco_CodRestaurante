package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.Pedido;

public class PedidoDAO {
	
	private Connection conn;

	public PedidoDAO(Connection conn) {
		this.conn = conn;
	}

	public void inserir(Pedido pedido) throws SQLException {
		String sql = "INSERT INTO item_x_conta (codconta, coditem, qtd, valor) VALUES (?, ?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, pedido.getCodConta());
		stmt.setInt(2, pedido.getCodItem());
		stmt.setInt(3, pedido.getQtd());
		stmt.setDouble(4, pedido.getValor());
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void alterar(Pedido pedido) throws SQLException {
		String sql = "UPDATE item_x_conta SET qtd = qtd + ?, valor = valor + ? WHERE codconta = ? AND coditem = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setInt(1, pedido.getQtd());
	    stmt.setDouble(2, pedido.getValor());
	    stmt.setInt(3, pedido.getCodConta());
	    stmt.setInt(4, pedido.getCodItem());
	    stmt.executeUpdate();
	    stmt.close();
	}
	
	public void deletar(Pedido pedido) throws SQLException {
		String sql = "DELETE FROM item_x_conta WHERE codconta = ? AND coditem = ?";		
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, pedido.getCodConta());
		stmt.setInt(2, pedido.getCodItem());
		stmt.executeUpdate();
		stmt.close();
	}
	
	public boolean repetirPedido(int codConta, int codItem) throws SQLException {
		String sql = "SELECT * FROM item_x_conta WHERE codconta = ? AND coditem = ?";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setInt(1, codConta);
	    stmt.setInt(2, codItem);
	    ResultSet rs = stmt.executeQuery();
	    
	    if (rs.next()) {
	    	rs.close();
		    stmt.close();
	    	return true; 
	    }
	    
	    rs.close();
	    stmt.close();
	    return false;
	}
	
	public List<Pedido> listarPedido() throws SQLException {
		List<Pedido> lista = new ArrayList<>();
		String sql = "SELECT * FROM item_x_conta ORDER BY codconta DESC";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			Pedido pedido = new Pedido();
			pedido.setCodConta(rs.getInt("codconta"));
			pedido.setCodItem(rs.getInt("coditem"));
			pedido.setQtd(rs.getInt("qtd"));
			pedido.setValor(rs.getFloat("valor"));

			lista.add(pedido);
		}
		
		return lista;
	}	
}

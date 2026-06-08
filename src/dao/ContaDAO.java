package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Conta;
import model.Pedido;

public class ContaDAO {

	private Connection conn;

	public ContaDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void criarConta(Conta conta) throws SQLException {
		String sql = "INSERT INTO conta (codmesa, total) VALUES (?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, conta.getCodMesa());
		stmt.setDouble(2, conta.getTotal());
		stmt.executeUpdate();
		stmt.close();
	}
	
	public List<Conta> listarContasAbertas() throws SQLException {
		List<Conta> lista = new ArrayList<>();
		String sql = "SELECT * FROM conta WHERE pago = 'N'";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			Conta conta = new Conta();
			conta.setCodigo(rs.getInt("codconta"));
			conta.setCodMesa(rs.getInt("codmesa"));
			conta.setTotal(rs.getFloat("total"));
			conta.setPago(rs.getString("pago").charAt(0));
			lista.add(conta);
		}
		
		return lista;
	}
	
	public void pagarConta(Conta conta) throws SQLException {
		String sql = "UPDATE conta SET pago = 'S' WHERE codconta = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, conta.getCodigo());
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void salvarTotalConta(Conta conta) throws SQLException {
		String sql = "UPDATE conta SET total = ? WHERE codconta = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setDouble(1, conta.getTotal());
		stmt.setInt(2, conta.getCodigo());
		stmt.executeUpdate();
		stmt.close();
	}
	
	public double buscarTotalConta(int codconta) {
	    double total = 0;
	    
	    try {
	    	String sql = "SELECT total FROM conta WHERE codconta = ?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, codconta);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            total = rs.getFloat("total");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return total;
	}
	
	public void alterarTotalConta(Pedido pedido) throws SQLException {
		String sql = "UPDATE conta SET total = total - ? WHERE codconta = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setDouble(1, pedido.getValor());
		stmt.setInt(2, pedido.getCodConta());
		stmt.executeUpdate();
		stmt.close();
	}
}

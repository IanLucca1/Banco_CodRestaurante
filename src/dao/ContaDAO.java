package dao;

import java.sql.*;
import model.Conta;

public class ContaDAO {

	private Connection conn;

	public ContaDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void criarConta(Conta conta) throws SQLException {
		String sql = "INSERT INTO conta (codcomanda, total) VALUES (?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, conta.getCodComanda());
		stmt.setFloat(2, conta.getTotal());
		stmt.executeUpdate();
		stmt.close();
	}
}

package dao;

import java.sql.*;
import model.Item;

public class ItemDAO {

	private Connection conn;

	public ItemDAO(Connection conn) {
		this.conn = conn;
	}

	public void inserir(Item item) throws SQLException {
		String sql = "INSERT INTO item (item, preco, codtipo) VALUES (?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, item.getItem());
		stmt.setDouble(2, item.getPreco());
		stmt.setInt(3, item.getCodTipo());
		stmt.executeUpdate();
		stmt.close();
	}
}

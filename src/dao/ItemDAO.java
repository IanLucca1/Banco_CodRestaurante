package dao;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
	public void alterar(Item item) throws SQLException {
		String sql = "UPDATE item SET item = ?, preco = ? WHERE coditem = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, item.getItem());
		stmt.setDouble(2, item.getPreco());
		stmt.setInt(3, item.getCodigo());
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void deletar(Item item) throws SQLException {
		String sql = "DELETE FROM item WHERE coditem = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, item.getCodigo());
		stmt.executeUpdate();
		stmt.close();
	}

	public ObservableList<Item> listarItens() throws SQLException {
	    ObservableList<Item> lista = FXCollections.observableArrayList();
	    String sql = "SELECT * FROM item";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    ResultSet rs = stmt.executeQuery();
	    while (rs.next()) {
	        Item item = new Item(
	        	rs.getInt("coditem"),
	            rs.getString("item"),
	            rs.getFloat("preco"),
	            rs.getInt("codtipo")
	        );	        
	        lista.add(item);
	    }
	    rs.close();
	    stmt.close();
	    return lista;
	}
}

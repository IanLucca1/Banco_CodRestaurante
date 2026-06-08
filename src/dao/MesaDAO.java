package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.Mesa;
import model.Tipo;

public class MesaDAO {

	private Connection conn;

	public MesaDAO(Connection conn) {
		this.conn = conn;
	}
	
	public int selecionarMesaDisponivel() throws SQLException {
		List<Integer> lista = new ArrayList<>();
		String sql = "SELECT codmesa FROM mesa WHERE emuso = 'N'";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			lista.add(rs.getInt("codmesa"));
		}
		
		if (lista.isEmpty()) {
	        return -1;
	    }
	    
	    Random random = new Random();
	    int indiceAleatorio = random.nextInt(lista.size());
	    
	    return lista.get(indiceAleatorio);
	}
	
	public List<Integer> listarMesasEmUso() throws SQLException {
		List<Integer> lista = new ArrayList<>();
		String sql = "SELECT codmesa FROM mesa WHERE emuso = 'S'";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			lista.add(rs.getInt("codmesa"));
		}
		
		return lista;
	}
	
	public void usarMesa(int codmesa) throws SQLException {
		String sql = "UPDATE mesa SET emuso = 'S' WHERE codmesa = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, codmesa);
		stmt.executeUpdate();
		stmt.close();
	}
	
	public void desocuparMesa(int codmesa) throws SQLException {
		String sql = "UPDATE mesa SET emuso = 'N' WHERE codmesa = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, codmesa);
		stmt.executeUpdate();
		stmt.close();
	}
}

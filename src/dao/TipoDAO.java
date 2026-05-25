package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Tipo;

public class TipoDAO {

	private Connection conn;

	public TipoDAO(Connection conn) {
		this.conn = conn;
	}
	
	public List<Tipo> listarTipos() throws SQLException {
		List<Tipo> lista = new ArrayList<>();
		String sql = "SELECT * FROM tipo";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			Tipo tipo = new Tipo();
			tipo.setTipo(rs.getString("tipo"));
			tipo.setCodigo(rs.getInt("codtipo"));
			lista.add(tipo);
		}
		
		return lista;
	}	
}

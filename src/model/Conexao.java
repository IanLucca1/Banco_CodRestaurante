package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexao {
	private static String server = "jdbc:sqlserver://10.109.8.9:1433;";
	private static String banco = "databaseName=DA123_Exerc_G02;";
	private static String usuario = "user=DA123_Exerc_G02;password=;encrypt=false;trustServerCertificate=true;loginTimeout=30;";
	public static Connection conexao;

	public static void conectar() {
		try {
			conexao = DriverManager.getConnection(server + banco + usuario);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Erro de conexão!\nERRO: " + ex.getMessage());
		}
	}

	public static void desconectar() {
		try {
			conexao.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão!\nERRO: " + ex.getMessage());
		}
	}
}

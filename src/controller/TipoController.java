package controller;

import java.util.ArrayList;
import java.util.List;

import dao.ItemDAO;
import dao.TipoDAO;
import javafx.fxml.FXML;
import model.Conexao;
import model.Item;
import model.Tipo;

public class TipoController {

	private TipoDAO dao;
	
	public TipoController() {
		super();
	}
	
	@FXML
	public List<Tipo> listarTipos() {
		List<Tipo> lista = new ArrayList<>();
		try {
			Conexao.conectar();
			dao = new TipoDAO(Conexao.conexao);
			lista = dao.listarTipos();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
		return lista;
	}	
}

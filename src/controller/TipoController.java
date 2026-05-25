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
	private Tipo tipo;
	
	public TipoController(Tipo tipo) {
		super();
		this.tipo = tipo;
	}
	
	public TipoController() {
		super();
	}
	
	@FXML
	public List<Tipo> listarTipos() {
		List<Tipo> lista = new ArrayList<>();
		try {
			Conexao.conectar(); // sua classe de conexão
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

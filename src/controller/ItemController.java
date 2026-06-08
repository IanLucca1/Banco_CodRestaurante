package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Conexao;
import model.Item;
import java.util.ArrayList;
import java.util.List;
import dao.ItemDAO;

public class ItemController {

	private ItemDAO dao;
	
	public ItemController() {
		super();
	}
	
	@FXML
	public boolean inserirItem(Item item) {
		try {
			Conexao.conectar();
			dao = new ItemDAO(Conexao.conexao);
			dao.inserir(item);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			Conexao.desconectar();
		}
	}
	
	public boolean alterarItem(Item item) {
		try {
			Conexao.conectar();
			dao = new ItemDAO(Conexao.conexao);
			dao.alterar(item);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			Conexao.desconectar();
		}
	}
	
	public boolean deletarItem(Item item) {
		try {
			Conexao.conectar();
			dao = new ItemDAO(Conexao.conexao);
			dao.deletar(item);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			Conexao.desconectar();
		}
	}
	
	public ObservableList<Item> listarItens() {
		try {
			Conexao.conectar();
			dao = new ItemDAO(Conexao.conexao);
			return dao.listarItens();
		} catch (Exception e) {
			e.printStackTrace();
			return FXCollections.observableArrayList();
		} finally {
			Conexao.desconectar();
		}
	}
}

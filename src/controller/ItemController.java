package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Conexao;
import model.Item;
import dao.ItemDAO;

public class ItemController {
    private ItemDAO dao;
	private Item item;
	
	public ItemController(Item item) {
		this.item = item;
	}
	
    @FXML
    public void salvarItem() {
        try {
            Conexao.conectar(); // sua classe de conexão
            dao = new ItemDAO(Conexao.conexao);
            dao.inserir(item);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Item salvo com sucesso!");
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	Conexao.desconectar();
        }
    }
}



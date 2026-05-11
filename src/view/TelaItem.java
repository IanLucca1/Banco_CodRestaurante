package view;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import controller.ItemController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Conexao;
import model.Item;

public class TelaItem {

    private TextField tfItem;
    private TextField tfPreco;
    private TextField tfTipo;
    private Label lblMensagem;
    
    private ItemController itemCtrl;
    
    public Scene getScene() {
		VBox root = new VBox(15);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(30));
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #f5f5f5);");

		Label lblTitulo = new Label("Cadastro de Itens");
		lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		lblTitulo.setTextFill(Color.DARKBLUE);

		HBox hboxItem = new HBox(30);
		hboxItem.setAlignment(Pos.CENTER_LEFT);
		Label lblItem = new Label("Item:");
		lblItem.setFont(Font.font(14));
		lblItem.setPrefWidth(85);
		lblItem.setMinWidth(60);
		tfItem = new TextField();
		tfItem.setPrefWidth(250);
		tfItem.setPromptText("Nome do Item");
		hboxItem.getChildren().addAll(lblItem, tfItem);

		HBox hboxPreco = new HBox(30);
		hboxPreco.setAlignment(Pos.CENTER_LEFT);
		Label lblPreco = new Label("Preço:");
		lblPreco.setFont(Font.font(14));
		lblPreco.setPrefWidth(85);
		lblPreco.setMinWidth(60);
		tfPreco = new TextField();
		tfPreco.setPrefWidth(250);
		tfPreco.setPromptText("Preço do item (Ex: 10.50)");
		hboxPreco.getChildren().addAll(lblPreco, tfPreco);

		HBox hboxTipo = new HBox(30);
		hboxTipo.setAlignment(Pos.CENTER_LEFT);
		Label lblTipo = new Label("Tipo:");
		lblTipo.setFont(Font.font(14));
		lblTipo.setPrefWidth(85);
		lblTipo.setMinWidth(60);
		tfTipo = new TextField();
		tfTipo.setPrefWidth(250);
		tfTipo.setPromptText("Código do item");
		hboxTipo.getChildren().addAll(lblTipo, tfTipo);

		Button btnSalvar = new Button("Salvar");
		btnSalvar.setPrefWidth(150);
		btnSalvar.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		btnSalvar.setStyle(
				"-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
		btnSalvar.setOnAction(e -> {
			try {
				handleSalvar();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		lblMensagem = new Label();
		lblMensagem.setTextFill(Color.RED);
		lblMensagem.setFont(Font.font(12));
		lblMensagem.setStyle("-fx-padding: 10 0 0 0;");

		root.getChildren().addAll(lblTitulo, hboxItem, hboxPreco, hboxTipo, btnSalvar, lblMensagem);

		Scene scene = new Scene(root, 450, 400);
		return scene;
	}
    
    private boolean validarCampos() {
        String item = tfItem.getText().trim();
        boolean tipoOk;
        boolean precoOk;
        
        try {
        	Integer.parseInt(tfTipo.getText().trim());
        	tipoOk = true;
        } catch (NumberFormatException e) {
        	tipoOk = false;
        }
        
        try {
        	Float.parseFloat(tfPreco.getText().trim());
        	precoOk = true;
        } catch (NumberFormatException e) {
        	precoOk = false;
        }
        
        return 	!item.isEmpty() && 
        		tipoOk &&
        		precoOk;
    }

	private void handleSalvar() throws SQLException {
		lblMensagem.setText("");
		if (validarCampos()) {
			lblMensagem.setTextFill(Color.GREEN);
			lblMensagem.setText("Cadastro realizado com sucesso!");
			
			String itemTxt = tfItem.getText().trim();
			float precoTxt = Float.parseFloat(tfPreco.getText().trim());
			int tipoTxt = Integer.parseInt(tfTipo.getText().trim());
			
		    Item item = new Item (itemTxt, precoTxt, tipoTxt);
		    itemCtrl = new ItemController(item);
		    itemCtrl.salvarItem();
		    
		} else {
			lblMensagem.setTextFill(Color.RED);
			lblMensagem.setText("Por favor, preencha todos os campos!");
			lblMensagem.requestFocus();
		}
	}
}

package view;

import java.sql.SQLException;
import javafx.scene.Node;
import controller.ItemController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class TelaEditarItem {

	private TextField tfItem;
	private TextField tfPreco;
	private Label lblMensagem;
	private ItemController itemCtrl = new ItemController();
	ObservableList<Item> opItens = FXCollections.observableArrayList(itemCtrl.listarItens());
	private ComboBox<Item> comboBox = new ComboBox<>(opItens);

	public Scene getScene() {
		VBox root = new VBox(18);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(30));
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #FCFBFA, #F3F1EE);");
 
		String estiloNav = "-fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;";

		Button btnVoltar = new Button("← Voltar");
		btnVoltar.setStyle("-fx-background-color: #7F8C8D; " + estiloNav);		
		btnVoltar.setOnAction(e -> {
			try {
				Home home = new Home();
				Scene sceneHome = home.getScene();
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.setScene(sceneHome);
				stage.setTitle("Home");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		Button btnTelaItem = new Button("Cadastrar Novo");
		btnTelaItem.setStyle("-fx-background-color: #556B2F; " + estiloNav);
		btnTelaItem.setOnAction(e -> {
			TelaItem telaItem = new TelaItem();
			Scene scene = telaItem.getScene();
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setTitle("Itens");
			stage.setScene(scene);
			stage.show();
		});
		
		Button btnTelaExcluirItem = new Button("Excluir Item");
		btnTelaExcluirItem.setStyle("-fx-background-color: #C0392B; " + estiloNav);
		btnTelaExcluirItem.setOnAction(e -> {
			TelaExcluirItem telaExcluirItem = new TelaExcluirItem();
			Scene scene = telaExcluirItem.getScene();
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setTitle("Excluir Item");
			stage.setScene(scene);
			stage.show();
		});
		
		HBox hboxNav = new HBox(10, btnVoltar, btnTelaItem, btnTelaExcluirItem);
		hboxNav.setAlignment(Pos.TOP_LEFT);

		Label lblTitulo = new Label("Editar Itens Cadastrados");
		lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
		lblTitulo.setTextFill(Color.web("#3E2723"));
		lblTitulo.setStyle("-fx-padding: 10 0;");

		String estiloLabel = "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #5D4037;";
		String estiloInput = "-fx-background-radius: 5; -fx-border-color: #BDC3C7; -fx-border-radius: 5; -fx-padding: 6;";

		HBox hboxCodItem = new HBox(20);
		hboxCodItem.setAlignment(Pos.CENTER_LEFT);
		Label lblCodItem = new Label("Selecionar:");
		lblCodItem.setStyle(estiloLabel);
		lblCodItem.setPrefWidth(95);
		comboBox.setPromptText("Selecione o item para alterar");
		comboBox.setPrefWidth(250);
		comboBox.setStyle("-fx-background-radius: 5; -fx-padding: 3;");
		comboBox.setConverter(new javafx.util.StringConverter<Item>() {
			@Override
			public String toString(Item item) {
				return item == null ? "" : item.getItem();
			}
			@Override
			public Item fromString(String string) {
				return null;
			}
		});
		hboxCodItem.getChildren().addAll(lblCodItem, comboBox);

		HBox hboxItem = new HBox(20);
		hboxItem.setAlignment(Pos.CENTER_LEFT);
		Label lblItem = new Label("Novo Nome:");
		lblItem.setStyle(estiloLabel);
		lblItem.setPrefWidth(95);
		tfItem = new TextField();
		tfItem.setPrefWidth(250);
		tfItem.setStyle(estiloInput);
		hboxItem.getChildren().addAll(lblItem, tfItem);

		HBox hboxPreco = new HBox(20);
		hboxPreco.setAlignment(Pos.CENTER_LEFT);
		Label lblPreco = new Label("Novo Preço:");
		lblPreco.setStyle(estiloLabel);
		lblPreco.setPrefWidth(95);
		tfPreco = new TextField();
		tfPreco.setPrefWidth(250);
		tfPreco.setStyle(estiloInput);
		hboxPreco.getChildren().addAll(lblPreco, tfPreco);

		// Evento para preencher automaticamente os campos ao selecionar o item
		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			if (newValue != null) {
				tfItem.setText(newValue.getItem());
				tfPreco.setText(String.valueOf(newValue.getPreco()));
			}
		});

		Button btnAlterar = new Button("Atualizar Dados");
		btnAlterar.setStyle("-fx-background-color: #D35400; -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10 25; -fx-cursor: hand;");
		btnAlterar.setOnAction(e -> {
			try {
				handleAlterarItem();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		HBox hboxBtns = new HBox(btnAlterar);
		hboxBtns.setAlignment(Pos.CENTER);
		hboxBtns.setStyle("-fx-padding: 15 0 0 0;");

		lblMensagem = new Label();
		lblMensagem.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
		lblMensagem.setStyle("-fx-padding: 10 0 0 0;");
		
		root.getChildren().addAll(hboxNav, lblTitulo, hboxCodItem, hboxItem, hboxPreco, hboxBtns, lblMensagem);
		Scene scene = new Scene(root, 480, 480);
		return scene;
	}

	private boolean validarCampos() {
		String item = tfItem.getText().trim();
		boolean codItemOk = comboBox.getValue() != null;
		boolean precoOk;
		try {
			Float.parseFloat(tfPreco.getText().trim());
			precoOk = true;
		} catch (NumberFormatException e) {
			precoOk = false;
		}
		return !item.isEmpty() && codItemOk && precoOk;
	}

	private void handleAlterarItem() throws SQLException {
		lblMensagem.setText("");
		if (validarCampos()) {
			Item itemSelecionado = comboBox.getValue();
			String itemTxt = tfItem.getText().trim();
			float precoTxt = Float.parseFloat(tfPreco.getText().trim());
			 
			Item item = new Item(itemSelecionado.getCodigo(), itemTxt, precoTxt, itemSelecionado.getCodTipo());
			itemCtrl.alterarItem(item);
			
			lblMensagem.setTextFill(Color.web("#27AE60"));
			lblMensagem.setText("✓ Item atualizado com sucesso!");
			
			// Atualiza a lista da combobox
			opItens.setAll(itemCtrl.listarItens());
			tfItem.clear();
			tfPreco.clear();
			comboBox.setValue(null);
		} else {
			lblMensagem.setTextFill(Color.web("#C0392B"));
			lblMensagem.setText("⚠ Erro ao atualizar. Verifique os dados inseridos.");
		}
	}
}
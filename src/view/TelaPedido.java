package view;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import controller.ContaController;
import controller.ItemController;
import controller.MesaController;
import controller.PedidoController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Conta;
import model.Item;
import model.Pedido;

public class TelaPedido {

	private Label lblMensagem;
	private MesaController mesaCtrl = new MesaController();
	private ContaController contaCtrl = new ContaController();
	private ItemController itemCtrl = new ItemController();
	private PedidoController pedidoCtrl = new PedidoController();
	ObservableList<Conta> opConta = FXCollections.observableArrayList(contaCtrl.listarContasAbertas());
	private ComboBox<Conta> comboBox = new ComboBox<Conta>(opConta);
	private TableView<Item> tabelaItens;
	private Map<Item, SimpleBooleanProperty> selecaoMap = new HashMap<>();
	private Map<Item, Integer> quantidadeMap = new HashMap<>();

	public Scene getScene() {
		VBox root = new VBox(15);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(25));
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
		
		Button btnExcluirPedido = new Button("Excluir Pedidos");
		btnExcluirPedido.setStyle("-fx-background-color: #C0392B; " + estiloNav);
		btnExcluirPedido.setOnAction(e -> {
			TelaExcluirPedido telaExcluir = new TelaExcluirPedido();
			Scene scene = telaExcluir.getScene();
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setTitle("Excluir Pedidos");
			stage.setScene(scene);
			stage.show();
		});
		
		HBox hboxNav = new HBox(10, btnVoltar, btnExcluirPedido);
		hboxNav.setAlignment(Pos.TOP_LEFT);

		Label lblTitulo = new Label("Lançamento de Pedidos");
		lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
		lblTitulo.setTextFill(Color.web("#3E2723"));

		Button btnCriarConta = new Button("+ Abrir Mesa");
		btnCriarConta.setStyle("-fx-background-color: #556B2F; -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 8 15; -fx-cursor: hand;");
		btnCriarConta.setOnAction(e -> {
			int codmesa = mesaCtrl.selecionarMesaDisponivel();
			if (codmesa == -1) {
				lblMensagem.setTextFill(Color.web("#C0392B"));
				lblMensagem.setText("⚠ Não há mesas disponíveis no momento!");
				return;
			}
			Conta conta = new Conta(codmesa, 0);
			contaCtrl.criarConta(conta);
			mesaCtrl.usarMesa(conta.getCodMesa());
			opConta.setAll(contaCtrl.listarContasAbertas());
			
			lblMensagem.setTextFill(Color.web("#27AE60"));
			lblMensagem.setText("✓ Conta aberta para a mesa " + codmesa);
		});

		HBox hboxMesa = new HBox(15);
		hboxMesa.setAlignment(Pos.CENTER_LEFT);
		Label lblMesa = new Label("Mesa Ativa:");
		lblMesa.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-weight: bold; -fx-text-fill: #5D4037; -fx-font-size: 14px;");
		comboBox.setPromptText("Selecione...");
		comboBox.setPrefWidth(140);
		comboBox.setStyle("-fx-background-radius: 5;");
		comboBox.setConverter(new javafx.util.StringConverter<Conta>() {
			@Override
			public String toString(Conta conta) {
				return conta == null ? "" : "Mesa " + conta.getCodMesa();
			}
			@Override
			public Conta fromString(String string) {
				return null;
			}
		});
		hboxMesa.getChildren().addAll(lblMesa, comboBox, btnCriarConta);

		tabelaItens = new TableView<>();
		tabelaItens.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tabelaItens.setEditable(true);
		tabelaItens.setPrefHeight(250);
		tabelaItens.setStyle("-fx-background-radius: 6; -fx-border-color: #BDC3C7; -fx-border-radius: 6;");
		
		TableColumn<Item, Boolean> colSelecionar = new TableColumn<>("Pedir");
		colSelecionar.setEditable(true);
		colSelecionar.setMaxWidth(60);
		colSelecionar.setMinWidth(50);
		colSelecionar.setCellValueFactory(cellData -> {
			Item item = cellData.getValue();
			return selecaoMap.computeIfAbsent(item, k -> new SimpleBooleanProperty(false));
		});
		colSelecionar.setCellFactory(CheckBoxTableCell.forTableColumn(colSelecionar));

		TableColumn<Item, String> colNome = new TableColumn<>("Item");
		colNome.setCellValueFactory(new PropertyValueFactory<>("item"));

		TableColumn<Item, Float> colPreco = new TableColumn<>("Preço");
		colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
		colPreco.setMaxWidth(100);

		TableColumn<Item, String> colQtd = new TableColumn<>("Qtd");
		colQtd.setMaxWidth(100);
		colQtd.setCellFactory(column -> new javafx.scene.control.TableCell<Item, String>() {
			private final TextField tfQtd = new TextField("1");
			{
				tfQtd.setAlignment(Pos.CENTER);
				tfQtd.setStyle("-fx-background-radius: 4; -fx-padding: 2 5;");
				tfQtd.textProperty().addListener((obs, oldV, newV) -> {
					if (!newV.matches("\\d*")) {
						tfQtd.setText(newV.replaceAll("[^\\d]", ""));
					}
					try {
						int qtd = tfQtd.getText().isEmpty() ? 1 : Integer.parseInt(tfQtd.getText());
						Item currentItem = (Item) getTableView().getItems().get(getIndex());
						quantidadeMap.put(currentItem, qtd);
					} catch (Exception ignored) {}
				});
			}

			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					Item currentItem = getTableView().getItems().get(getIndex());
					int qtdAtual = quantidadeMap.getOrDefault(currentItem, 1);
					tfQtd.setText(String.valueOf(qtdAtual));
					setGraphic(tfQtd);
				}
			}
		});

		tabelaItens.getColumns().addAll(colSelecionar, colNome, colPreco, colQtd);
		tabelaItens.setItems(FXCollections.observableArrayList(itemCtrl.listarItens()));

		Button btnConfirmarPedido = new Button("Confirmar Lançamento");
		btnConfirmarPedido.setStyle("-fx-background-color: #D35400; -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10 30; -fx-cursor: hand;");
		btnConfirmarPedido.setOnAction(e -> {
			try {
				handleInserirPedido();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		lblMensagem = new Label();
		lblMensagem.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));

		VBox.setVgrow(tabelaItens, Priority.ALWAYS);
		root.getChildren().addAll(hboxNav, lblTitulo, hboxMesa, tabelaItens, btnConfirmarPedido, lblMensagem);
		return new Scene(root, 550, 520);
	}

	private boolean validarCampos() {
		if (comboBox.getValue() == null) {
			lblMensagem.setTextFill(Color.web("#C0392B"));
			lblMensagem.setText("⚠ Selecione uma mesa ativa para associar o pedido!");
			return false;
		}
		boolean temItemSelecionado = selecaoMap.values().stream().anyMatch(SimpleBooleanProperty::get);
		if (!temItemSelecionado) {
			lblMensagem.setTextFill(Color.web("#C0392B"));
			lblMensagem.setText("⚠ Selecione pelo menos um item da tabela!");
			return false;
		}
		return true;
	}

	private void handleInserirPedido() throws SQLException {
		lblMensagem.setText("");
		if (validarCampos()) {
			Conta conta = comboBox.getValue();
		 
			selecaoMap.forEach((itemSelecionado, selecionadoProp) -> {
				if (selecionadoProp.get()) {
					int qtd = quantidadeMap.getOrDefault(itemSelecionado, 1);
			 
					Pedido pedido = new Pedido(conta.getCodigo(), itemSelecionado.getCodigo(), qtd, 0);
					pedido.calcularValor(itemSelecionado.getPreco());
					boolean repetirPedido = pedidoCtrl.repetirPedido(pedido.getCodConta(), pedido.getCodItem());	
					if (repetirPedido) {
						pedidoCtrl.alterar(pedido);
					} else {
						pedidoCtrl.inserir(pedido);
					}
					conta.calcularValor(pedido.getValor());
				}
			});
			
			contaCtrl.salvarTotalConta(conta);
			
			lblMensagem.setTextFill(Color.web("#27AE60"));
			lblMensagem.setText("✓ Pedidos enviados à cozinha com sucesso!");

			selecaoMap.keySet().forEach(item -> selecaoMap.get(item).set(false));
			quantidadeMap.clear();
			tabelaItens.refresh();
		}
	}
}
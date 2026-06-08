package view;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controller.ItemController;
import controller.PedidoController;
import controller.TipoController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Item;
import model.Pedido;
import model.Tipo;

public class TelaRelatorio {
	
	private TipoController tipoCtrl = new TipoController();
	private PedidoController pedidoCtrl = new PedidoController();
	private ObservableList<Tipo> opTipo = FXCollections.observableArrayList(tipoCtrl.listarTipos());
	private ComboBox<Tipo> comboBox = new ComboBox<>(opTipo);
	private TextField tfVlrPedido;
	TableView<Pedido> tabela = new TableView<>();
	private ObservableList<Pedido> todosOsDados = FXCollections.observableArrayList();

	public Scene getScene() {
		VBox root = new VBox(15);
		root.setPadding(new Insets(25));
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #FCFBFA, #F3F1EE);");

		Button btnVoltar = new Button("← Voltar");
		btnVoltar.setStyle("-fx-background-color: #7F8C8D; -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;");
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

		comboBox.setPromptText("Filtrar Categoria");
		comboBox.setPrefWidth(150);
		comboBox.setStyle("-fx-background-radius: 4;");
		comboBox.setConverter(new javafx.util.StringConverter<Tipo>() {
			@Override
			public String toString(Tipo tipo) {
				return tipo == null ? "" : tipo.getTipo();
			}
			@Override
			public Tipo fromString(String string) {
				return null;
			}
		});

		tfVlrPedido = new TextField();
		tfVlrPedido.setPrefWidth(120);
		tfVlrPedido.setPromptText("Valor Mínimo (R$)");
		tfVlrPedido.setStyle("-fx-background-radius: 4; -fx-padding: 5;");

		Button btnAplicarFiltro = new Button("Filtrar");
		btnAplicarFiltro.setStyle("-fx-background-color: #556B2F; -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 6 15; -fx-cursor: hand;");
		btnAplicarFiltro.setOnAction(e -> aplicarFiltros());

		Button btnLimparFiltro = new Button("Limpar");
		btnLimparFiltro.setStyle("-fx-background-color: #D35400; -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-weight: bold; -fx-background-radius: 4; -fx-padding: 6 15; -fx-cursor: hand;");
		btnLimparFiltro.setOnAction(e -> {
			comboBox.setValue(null);
			tfVlrPedido.clear();
			tfVlrPedido.setStyle("-fx-background-radius: 4; -fx-padding: 5;");
			tabela.setItems(todosOsDados);
		});

		HBox header = new HBox(12);
		header.setAlignment(Pos.CENTER_LEFT);
		header.getChildren().addAll(btnVoltar, comboBox, tfVlrPedido, btnAplicarFiltro, btnLimparFiltro);

		tabela.setStyle("-fx-background-radius: 6; -fx-border-color: #BDC3C7; -fx-border-radius: 6;");
		tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<Pedido, Integer> colId = new TableColumn<>("Cód. Conta");
		colId.setCellValueFactory(new PropertyValueFactory<>("codConta"));
		colId.setMaxWidth(100);

		TableColumn<Pedido, Integer> colItem = new TableColumn<>("Cód. Item");
		colItem.setCellValueFactory(new PropertyValueFactory<>("codItem"));
		colItem.setMaxWidth(100);

		TableColumn<Pedido, Integer> colQtd = new TableColumn<>("Qtd. Vendida");
		colQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
		colQtd.setMaxWidth(120);

		TableColumn<Pedido, Double> colPreco = new TableColumn<>("Faturamento Total (R$)");
		colPreco.setCellValueFactory(new PropertyValueFactory<>("valor"));

		tabela.getColumns().addAll(colId, colItem, colQtd, colPreco);
		todosOsDados.setAll(pedidoCtrl.listarPedido());
		tabela.setItems(todosOsDados);
		
		VBox.setVgrow(tabela, Priority.ALWAYS);
		root.getChildren().addAll(header, tabela);
		return new Scene(root, 680, 480);
	}
	
	private void aplicarFiltros() {
		ObservableList<Pedido> filtrados = FXCollections.observableArrayList(todosOsDados);
		
		Tipo tipoSelecionado = comboBox.getValue();
		if (tipoSelecionado != null) {
			Set<Integer> itensDoTipo = buscarItensPorTipo(tipoSelecionado.getCodigo());
			filtrados.removeIf(p -> !itensDoTipo.contains(p.getCodItem()));
		}
		
		String valorTexto = tfVlrPedido.getText().trim();
		if (!valorTexto.isEmpty()) {
			try {
				double valorMin = Double.parseDouble(valorTexto);
				tfVlrPedido.setStyle("-fx-background-radius: 4; -fx-padding: 5;");
				filtrados.removeIf(p -> p.getValor() < valorMin);
			} catch (NumberFormatException e) {
				tfVlrPedido.setStyle("-fx-background-radius: 4; -fx-padding: 5; -fx-border-color: #C0392B; -fx-border-radius: 4;");
				return;
			}
		}
				
		tabela.setItems(filtrados);
	}

	private Set<Integer> buscarItensPorTipo(int codTipo) {
		ItemController itemCtrl = new ItemController();
		List<Item> todosItens = itemCtrl.listarItens();
		Set<Integer> idsFiltrados = new HashSet<>();
		for (Item item : todosItens) {
			if (item.getCodTipo() == codTipo) {
				idsFiltrados.add(item.getCodigo());
			}
		}
		return idsFiltrados;
	}
}
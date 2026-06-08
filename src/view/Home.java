package view;

import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Home {

	public Scene getScene() throws SQLException {
		VBox root = new VBox(20);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(40));
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #FCFBFA, #F3F1EE);");
		
		Label lblTitulo = new Label("Sabor & Gestão");
		lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
		lblTitulo.setTextFill(Color.web("#3E2723"));
		
		Label lblSubtitulo = new Label("Sistema de Gerenciamento de Restaurante");
		lblSubtitulo.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
		lblSubtitulo.setTextFill(Color.web("#7F8C8D"));
		lblSubtitulo.setStyle("-fx-padding: 0 0 20 0;");

		// Estilo padrão para os botões do Menu Principal
		String estiloBotaoMenu = "-fx-background-color: #556B2F; " +
				"-fx-text-fill: white; " +
				"-fx-background-radius: 8; " +
				"-fx-padding: 12 30; " +
				"-fx-font-family: 'Segoe UI'; " +
				"-fx-font-size: 15; " +
				"-fx-font-weight: bold; " +
				"-fx-cursor: hand; " +
				"-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);";

		Button btnTelaItem = new Button("Gerenciar Itens");
		btnTelaItem.setPrefWidth(220);
		btnTelaItem.setStyle(estiloBotaoMenu);
		btnTelaItem.setOnAction(e -> {
			TelaItem telaItem = new TelaItem();
			Scene scene = telaItem.getScene();
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setTitle("Itens");
			stage.setScene(scene);
			stage.show();
		});

		Button btnTelaPedido = new Button("Painel de Pedidos");
		btnTelaPedido.setPrefWidth(220);
		btnTelaPedido.setStyle(estiloBotaoMenu);
		btnTelaPedido.setOnAction(e -> {
			TelaPedido telaPedido = new TelaPedido();
			Scene scene = telaPedido.getScene();
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setTitle("Pedidos");
			stage.setScene(scene);
			stage.show();
		});

		Button btnTelaRelatorio = new Button("Relatórios de Vendas");
		btnTelaRelatorio.setPrefWidth(220);
		btnTelaRelatorio.setStyle(estiloBotaoMenu);
		btnTelaRelatorio.setOnAction(e -> {
			TelaRelatorio telaRelatorio = new TelaRelatorio();
			Scene scene = telaRelatorio.getScene();
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setTitle("Relatórios");
			stage.setScene(scene);
			stage.show();
		});
		
		Button btnPagarConta = new Button("Efetuar Pagamento");
		btnPagarConta.setPrefWidth(220);
		btnPagarConta.setStyle("-fx-background-color: #D35400; " + // Destaque para o caixa
				"-fx-text-fill: white; " +
				"-fx-background-radius: 8; " +
				"-fx-padding: 12 30; " +
				"-fx-font-family: 'Segoe UI'; " +
				"-fx-font-size: 15; " +
				"-fx-font-weight: bold; " +
				"-fx-cursor: hand; " +
				"-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
		btnPagarConta.setOnAction(e -> {
			TelaPagamento telaPagamento = new TelaPagamento();
			Scene scene = telaPagamento.getScene();
			Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			stage.setTitle("Pagamento");
			stage.setScene(scene);
			stage.show();
		});

		root.getChildren().addAll(lblTitulo, lblSubtitulo, btnTelaItem, btnTelaPedido, btnTelaRelatorio, btnPagarConta);
		return new Scene(root, 500, 500);
	}
}
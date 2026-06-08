package view;

import java.sql.SQLException;
import javafx.scene.Node;
import controller.ContaController;
import controller.MesaController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Conta;

public class TelaPagamento {

	private Label lblMensagem;
	private MesaController mesaCtrl = new MesaController();
	private ContaController contaCtrl = new ContaController();
	private ComboBox<Conta> comboBox = new ComboBox<>(FXCollections.observableArrayList(contaCtrl.listarContasAbertas()));
	private Label lblTotalPagar;

	public Scene getScene() {
		VBox root = new VBox(20);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(35));
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #FCFBFA, #F3F1EE);");

		HBox hboxVoltar = new HBox();
		hboxVoltar.setAlignment(Pos.TOP_LEFT);
		Button btnVoltar = new Button("← Voltar ao Menu");
		btnVoltar.setStyle("-fx-background-color: #7F8C8D; -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;");
		hboxVoltar.getChildren().add(btnVoltar);

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

		Label lblTitulo = new Label("Fechamento de Caixa");
		lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
		lblTitulo.setTextFill(Color.web("#3E2723"));

		lblTotalPagar = new Label("Total a Pagar: R$ 0,00");
		lblTotalPagar.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
		lblTotalPagar.setTextFill(Color.web("#27AE60"));
		lblTotalPagar.setStyle("-fx-background-color: #E8F8F5; -fx-padding: 10 20; -fx-background-radius: 6; -fx-border-color: #A3E4D7; -fx-border-radius: 6;");

		HBox hboxMesa = new HBox(15);
		hboxMesa.setAlignment(Pos.CENTER);
		Label lblMesa = new Label("Mesa em Atendimento:");
		lblMesa.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #5D4037;");
		
		comboBox.setPromptText("Selecione a mesa");
		comboBox.setPrefWidth(180);
		comboBox.setStyle("-fx-background-radius: 5;");
		comboBox.setConverter(new javafx.util.StringConverter<Conta>() {
			@Override
			public String toString(Conta conta) {
				return conta == null ? "" : "Mesa " + conta.getCodMesa() + " (Conta #" + conta.getCodigo() + ")";
			}
			@Override
			public Conta fromString(String string) {
				return null;
			}
		});

		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			if (newValue != null) {
				lblTotalPagar.setText(String.format("Total a Pagar: R$ %.2f", newValue.getTotal()));
			} else {
				lblTotalPagar.setText("Total a Pagar: R$ 0,00");
			}
		});
		hboxMesa.getChildren().addAll(lblMesa, comboBox);

		Button btnPagar = new Button("✓ Confirmar Pagamento e Liberar Mesa");
		btnPagar.setStyle("-fx-background-color: #556B2F; -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 12 25; -fx-cursor: hand;");
		btnPagar.setOnAction(e -> {
			try {
				handlePagamento();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		lblMensagem = new Label();
		lblMensagem.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
		lblMensagem.setStyle("-fx-padding: 5 0 0 0;");
		
		root.getChildren().addAll(hboxVoltar, lblTitulo, hboxMesa, lblTotalPagar, btnPagar, lblMensagem);
		return new Scene(root, 520, 440);
	}

	private boolean validarCampos() {
		return comboBox.getValue() != null;
	}

	private void handlePagamento() throws SQLException {
		lblMensagem.setText("");
		if (validarCampos()) {
			Conta contaSelecionada = comboBox.getValue();
			
			contaCtrl.pagarConta(contaSelecionada);
			mesaCtrl.desocuparMesa(contaSelecionada.getCodMesa());
			
			lblMensagem.setTextFill(Color.web("#27AE60"));
			lblMensagem.setText("✓ Pagamento processado. Mesa liberada com sucesso!");
			
			// Atualiza a lista de mesas em aberto
			comboBox.setItems(FXCollections.observableArrayList(contaCtrl.listarContasAbertas()));
			comboBox.setValue(null);
			lblTotalPagar.setText("Total a Pagar: R$ 0,00");
		} else {
			lblMensagem.setTextFill(Color.web("#C0392B"));
			lblMensagem.setText("⚠ Por favor, selecione uma mesa para efetuar a baixa.");
		}
	}
}
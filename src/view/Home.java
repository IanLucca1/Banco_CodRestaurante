package view;

import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Home {

	public Scene getScene() {
		VBox root = new VBox(15);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(30));
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #f5f5f5);");
		Label lblTitulo = new Label("Home");
		lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		lblTitulo.setTextFill(Color.DARKBLUE);
		
		Button btnTelaItem = new Button("Cadastrar novos itens");
		btnTelaItem.setPrefWidth(150);
		btnTelaItem.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		btnTelaItem.setStyle(
				"-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
		
		TelaItem telaItem = new TelaItem();
		Scene scene = telaItem.getScene();
		
		btnTelaItem.setOnAction(e ->  {
			
	        });
		
		// Cria a Scene com tamanho fixo (400x350)
        return scene;
	}
	
	public void chamarTelaItem() {
		TelaItem telaItem = new TelaItem();
		Scene scene = telaItem.getScene();
	}
}

package view;

import model.Conexao;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.TelaItem;
import javafx.application.Application;

public class Principal extends Application{

    @Override
    public void start(Stage stage) {
        // Cria a tela de login
        TelaItem telaLogin = new TelaItem();
        Scene scene = telaLogin.getScene();

        // Configura o Stage (janela)
        stage.setTitle("Sistema de Comanda - Login");
        stage.setScene(scene);
        stage.setResizable(false); // impede redimensionamento
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

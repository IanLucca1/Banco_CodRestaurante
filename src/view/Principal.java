package view;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {

    @Override
    public void start(Stage stage) {
        Home home = new Home();
        Scene scene;
		
        try {
			scene = home.getScene();
			stage.setTitle("Home");
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.show();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
        launch(args);
    }

}

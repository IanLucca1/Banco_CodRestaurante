package view;
 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Item;
import controller.ItemController;
 
public class TelaExcluirItem {
    
    private ItemController itemCtrl = new ItemController();
    private TableView<Item> tabelaProdutos;
 
    public Scene getScene() {
        VBox root = new VBox(15);
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
        
        Button btnTelaEditarItem = new Button("Editar Item");
        btnTelaEditarItem.setStyle("-fx-background-color: #D35400; " + estiloNav);
        btnTelaEditarItem.setOnAction(e -> {
        	TelaEditarItem telaEditarItem = new TelaEditarItem();
        	Scene scene = telaEditarItem.getScene();
        	Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        	stage.setTitle("Editar Item");
        	stage.setScene(scene);
        	stage.show();
        });
        
        HBox hboxNav = new HBox(10, btnVoltar, btnTelaItem, btnTelaEditarItem);
        hboxNav.setAlignment(Pos.TOP_LEFT);
 
        Label lblTitulo = new Label("Remover Itens do Cardápio");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblTitulo.setTextFill(Color.web("#3E2723"));
        lblTitulo.setStyle("-fx-padding: 5 0;");
 
        tabelaProdutos = new TableView<>();
        tabelaProdutos.setEditable(true);
        tabelaProdutos.setPrefHeight(280);
        tabelaProdutos.setStyle("-fx-background-radius: 6; -fx-border-color: #BDC3C7; -fx-border-radius: 6;");
        tabelaProdutos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        Map<Item, SimpleBooleanProperty> selecaoMap = new HashMap<>();
        
        TableColumn<Item, Boolean> colSelecionar = new TableColumn<>("Selecionar");
        colSelecionar.setEditable(true);
        colSelecionar.setPrefWidth(80);
        colSelecionar.setCellValueFactory(cellData -> {
            Item item = cellData.getValue();
            return selecaoMap.computeIfAbsent(item, k -> new SimpleBooleanProperty(false));
        });
        colSelecionar.setCellFactory(CheckBoxTableCell.forTableColumn(colSelecionar));
        
        TableColumn<Item, String> colNome = new TableColumn<>("Nome do Produto");
        colNome.setCellValueFactory(new PropertyValueFactory<>("item"));
        
        TableColumn<Item, Float> colPreco = new TableColumn<>("Preço (R$)");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colPreco.setPrefWidth(100);
        
        tabelaProdutos.getColumns().addAll(colSelecionar, colNome, colPreco);
        
        ObservableList<Item> listaItens = FXCollections.observableArrayList(itemCtrl.listarItens());
        tabelaProdutos.setItems(listaItens);
 
        Button btnExcluir = new Button("Excluir Itens Selecionados");
        btnExcluir.setStyle("-fx-background-color: #C0392B; -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10 25; -fx-cursor: hand;");
        btnExcluir.setOnAction(e -> {
             List<Item> paraRemover = new ArrayList<>();
             selecaoMap.forEach((item, selecionado) -> {
                 if (selecionado.get()) {
                     paraRemover.add(item);
                 }
             });
 
             if (paraRemover.isEmpty()) {
                 Alert alerta = new Alert(Alert.AlertType.WARNING);
                 alerta.setTitle("Aviso");
                 alerta.setHeaderText(null);
                 alerta.setContentText("Por favor, selecione pelo menos um item para exclusão.");
                 alerta.showAndWait();
                 return;
             }
 
             Alert alertConf = new Alert(Alert.AlertType.CONFIRMATION);
             alertConf.setTitle("Confirmar Exclusão");
             alertConf.setHeaderText(null);
             alertConf.setContentText("Deseja realmente excluir os " + paraRemover.size() + " itens selecionados?");
             
             if (alertConf.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                 int sucessoCount = 0;
                 int falhaCount = 0;
                 List<Item> itensRemovidosComSucesso = new ArrayList<>();
 
                 for (Item item : paraRemover) {
                     boolean excluido = itemCtrl.deletarItem(item);
                     if (excluido) {
                         selecaoMap.remove(item);
                         itensRemovidosComSucesso.add(item);
                         sucessoCount++;
                     } else {
                         falhaCount++;
                     }
                 }
                 
                 tabelaProdutos.getItems().removeAll(itensRemovidosComSucesso);
 
                 if (falhaCount > 0) {
                     Alert alertaErro = new Alert(Alert.AlertType.ERROR);
                     alertaErro.setTitle("Atenção na Exclusão");
                     alertaErro.setHeaderText(null);
                     alertaErro.setContentText(sucessoCount + " item(ns) excluído(s).\n" + 
                                                falhaCount + " item(ns) não puderam ser excluídos pois já possuem pedidos vinculados!");
                     alertaErro.showAndWait();
                 } else {
                     Alert alertaSucesso = new Alert(Alert.AlertType.INFORMATION);
                     alertaSucesso.setTitle("Sucesso");
                     alertaSucesso.setHeaderText(null);
                     alertaSucesso.setContentText("Todos os itens selecionados foram excluídos com sucesso!");
                     alertaSucesso.showAndWait();
                 }
             }    
        });
 
        VBox.setVgrow(tabelaProdutos, Priority.ALWAYS);
        root.getChildren().addAll(hboxNav, lblTitulo, tabelaProdutos, btnExcluir);
        return new Scene(root, 520, 500);
    }
}
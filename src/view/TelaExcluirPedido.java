package view;

import java.util.ArrayList;
import java.util.List;

import controller.ContaController;
import controller.PedidoController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import model.Pedido;

public class TelaExcluirPedido {

    private PedidoController pedidoCtrl = new PedidoController();
    private ContaController contaCtrl = new ContaController();
    private TableView<PedidoSelection> tabelaPedidos;
    private ObservableList<PedidoSelection> listaPedidosSelection = FXCollections.observableArrayList();

    public static class PedidoSelection {
        private final Pedido pedido;
        private final SimpleBooleanProperty selecionado = new SimpleBooleanProperty(false);
        private final SimpleStringProperty infoConta = new SimpleStringProperty();

        public PedidoSelection(Pedido pedido) {
            this.pedido = pedido;
            this.infoConta.set("Conta #" + pedido.getCodConta() + " | Item ID: " + pedido.getCodItem());
        }

        public Pedido getPedido() { return pedido; }
        public SimpleBooleanProperty selecionadoProperty() { return selecionado; }
        public boolean isSelecionado() { return selecionado.get(); }
        public void setSelecionado(boolean selecionado) { this.selecionado.set(selecionado); }
        public String getInfoConta() { return infoConta.get(); }
    }

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
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setTitle("Home");
                stage.setScene(home.getScene());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        Button btnTelaItem = new Button("Gerenciar Cardápio");
        btnTelaItem.setStyle("-fx-background-color: #556B2F; " + estiloNav);
        btnTelaItem.setOnAction(e -> {
            TelaItem telaItem = new TelaItem();
            Scene scene = telaItem.getScene();
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setTitle("Itens");
            stage.setScene(scene);
            stage.show();
        });

        HBox hboxNav = new HBox(10, btnVoltar, btnTelaItem);
        hboxNav.setAlignment(Pos.CENTER_LEFT);

        Label lblTitulo = new Label("Estorno e Exclusão de Pedidos");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblTitulo.setTextFill(Color.web("#3E2723"));

        tabelaPedidos = new TableView<>();
        tabelaPedidos.setEditable(true);
        tabelaPedidos.setStyle("-fx-background-radius: 6; -fx-border-color: #BDC3C7; -fx-border-radius: 6;");
        tabelaPedidos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<PedidoSelection, Boolean> colSel = new TableColumn<>("Selecionar");
        colSel.setEditable(true);
        colSel.setMaxWidth(90);
        colSel.setCellValueFactory(c -> c.getValue().selecionadoProperty());
        colSel.setCellFactory(CheckBoxTableCell.forTableColumn(colSel));

        TableColumn<PedidoSelection, String> colInfo = new TableColumn<>("Vínculo do Pedido");
        colInfo.setCellValueFactory(new PropertyValueFactory<>("infoConta"));

        TableColumn<PedidoSelection, Integer> colQtd = new TableColumn<>("Quantidade");
        colQtd.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getPedido().getQtd()).asObject());
        colQtd.setMaxWidth(100);

        TableColumn<PedidoSelection, Double> colVal = new TableColumn<>("Valor Total (R$)");
        colVal.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPedido().getValor()).asObject());
        colVal.setMaxWidth(140);

        tabelaPedidos.getColumns().addAll(colSel, colInfo, colQtd, colVal);
        carregarPedidos();

        Button btnExcluir = new Button("Estornar Pedidos Selecionados");
        btnExcluir.setStyle("-fx-background-color: #C0392B; -fx-text-fill: white; -fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 10 25; -fx-cursor: hand;");
        btnExcluir.setOnAction(e -> {
            List<PedidoSelection> selecionados = new ArrayList<>();
            for (PedidoSelection sel : listaPedidosSelection) {
                if (sel.isSelecionado()) {
                    selecionados.add(sel);
                }
            }

            if (selecionados.isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Aviso");
                alerta.setHeaderText(null);
                alerta.setContentText("Nenhum pedido foi selecionado para exclusão.");
                alerta.showAndWait();
                return;
            }

            int sucessos = 0;
            for (PedidoSelection sel : selecionados) {
                Pedido pedido = sel.getPedido();
                try {                 
                    contaCtrl.alterarTotalConta(pedido);
                    pedidoCtrl.deletar(pedido);
                    sucessos++;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            Alert alertaResultado = new Alert(Alert.AlertType.INFORMATION);
            alertaResultado.setTitle("Processamento de Exclusão");
            alertaResultado.setHeaderText(null);
            alertaResultado.setContentText(sucessos + " pedido(s) excluído(s) e saldo(s) de conta(s) recalculado(s) com sucesso!");
            alertaResultado.showAndWait();
            
            carregarPedidos();
        });

        root.getChildren().addAll(hboxNav, lblTitulo, tabelaPedidos, btnExcluir);
        VBox.setVgrow(tabelaPedidos, Priority.ALWAYS);

        return new Scene(root, 560, 500);
    }

    private void carregarPedidos() {
        try {
            listaPedidosSelection.clear();
            List<Pedido> pedidosDoBanco = pedidoCtrl.listarPedido();
            for (Pedido p : pedidosDoBanco) {
                listaPedidosSelection.add(new PedidoSelection(p));
            }
            tabelaPedidos.setItems(listaPedidosSelection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
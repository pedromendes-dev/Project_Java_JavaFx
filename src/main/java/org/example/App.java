package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Aplicação JavaFX principal para o sistema PAG_REC (Contas a Pagar e Receber).
 */
public class App extends Application {
    @Override
    public void start(Stage stage) {
        Label title = new Label("PAG_REC");
        title.getStyleClass().add("app-title");

        // usar escapes Unicode para evitar problemas de encoding na UI
        Label subtitle = new Label("Gest\u00E3o de Contas a Pagar e Receber");
        subtitle.getStyleClass().add("app-subtitle");

        // Tiles (cada botão abre uma janela)
        Button btnContas = new Button("Cadastro de\nContas");
        Button btnClientes = new Button("Cadastro de\nClientes");
        Button btnMov = new Button("Lan\u00E7ar\nMovimenta\u00E7\u00E3o");

        btnContas.getStyleClass().add("tile");
        btnClientes.getStyleClass().add("tile");
        btnMov.getStyleClass().add("tile");

        // make buttons grow horizontally when window resizes
        btnContas.setMaxWidth(Double.MAX_VALUE);
        btnClientes.setMaxWidth(Double.MAX_VALUE);
        btnMov.setMaxWidth(Double.MAX_VALUE);

        HBox tiles = new HBox(20, btnClientes, btnContas, btnMov);
        tiles.setAlignment(Pos.CENTER);
        tiles.setPadding(new Insets(10));
        HBox.setHgrow(btnClientes, Priority.ALWAYS);
        HBox.setHgrow(btnContas, Priority.ALWAYS);
        HBox.setHgrow(btnMov, Priority.ALWAYS);

        VBox centerBox = new VBox(12, subtitle, tiles);
        centerBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setMargin(title, new Insets(20, 0, 10, 0));
        root.setCenter(centerBox);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 900, 420);
        // carrega stylesheet
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());

        stage.setTitle("PAG_REC");
        stage.setScene(scene);
        stage.show();

        // handlers: abrem novas janelas com as telas correspondentes
        btnClientes.setOnAction(e -> openScreen("/fxml/Clientes.fxml", "Cadastro de Clientes"));
        btnContas.setOnAction(e -> openScreen("/fxml/Contas.fxml", "Cadastro de Contas"));
        // usar escapes Unicode no título para evitar problemas de encoding no sistema
        btnMov.setOnAction(e -> openScreen("/fxml/Movimentacao.fxml", "Lan\u00E7amento de Movimenta\u00E7\u00E3o"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void openScreen(String fxmlPath, String title) {
        try {
            javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initOwner(null);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setHeaderText("Erro ao abrir a tela");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}

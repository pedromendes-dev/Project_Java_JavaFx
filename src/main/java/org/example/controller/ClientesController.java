package org.example.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.model.Cliente;
import org.example.repository.ClienteDAO;
import org.example.util.ValidationUtil;

import java.util.List;

public class ClientesController {
    @FXML private TextField tfNome;
    @FXML private TextField tfDocumento;
    @FXML private TextField tfEmail;
    @FXML private TableView<Cliente> tableClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colDocumento;
    @FXML private TableColumn<Cliente, String> colEmail;

    private final ClienteDAO dao = new ClienteDAO();

    @FXML
    public void initialize() {
        // configurar colunas usando nomes das propriedades do modelo Cliente
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // listener para selecionar linha e popular campos
        tableClientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Cliente>() {
            @Override
            public void changed(ObservableValue<? extends Cliente> observable, Cliente oldValue, Cliente newValue) {
                if (newValue != null) {
                    tfNome.setText(newValue.getNome());
                    tfDocumento.setText(newValue.getDocumento());
                    tfEmail.setText(newValue.getEmail());
                } else {
                    tfNome.clear();
                    tfDocumento.clear();
                    tfEmail.clear();
                }
            }
        });

        loadAll();
    }

    private void loadAll() {
        try {
            List<Cliente> list = dao.findAll();
            tableClientes.getItems().clear();
            tableClientes.getItems().addAll(list);
        } catch (Exception e) {
            showError("Erro ao carregar clientes", e);
        }
    }

    @FXML
    private void onIncluir() {
        try {
            String nome = tfNome.getText().trim();
            String doc = tfDocumento.getText().trim();
            String email = tfEmail.getText().trim();

            if (!ValidationUtil.isNotEmpty(nome)) {
                showWarning("Nome é obrigatório");
                return;
            }
            if (!ValidationUtil.isValidDocument(doc)) {
                showWarning("Documento inválido");
                return;
            }
            if (!ValidationUtil.isValidEmail(email)) {
                showWarning("Email inválido");
                return;
            }

            Cliente c = new Cliente(nome, doc, email);
            dao.insert(c);
            loadAll();
            clearFields();
        } catch (Exception e) {
            showError("Erro ao incluir cliente", e);
        }
    }

    @FXML
    private void onAlterar() {
        Cliente selected = tableClientes.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selecione um cliente para alterar");
            return;
        }
        try {
            String nome = tfNome.getText().trim();
            String doc = tfDocumento.getText().trim();
            String email = tfEmail.getText().trim();

            if (!ValidationUtil.isNotEmpty(nome)) {
                showWarning("Nome é obrigatório");
                return;
            }
            if (!ValidationUtil.isValidDocument(doc)) {
                showWarning("Documento inválido");
                return;
            }
            if (!ValidationUtil.isValidEmail(email)) {
                showWarning("Email inválido");
                return;
            }

            selected.setNome(nome);
            selected.setDocumento(doc);
            selected.setEmail(email);
            dao.update(selected);
            loadAll();
            clearFields();
        } catch (Exception e) {
            showError("Erro ao alterar cliente", e);
        }
    }

    @FXML
    private void onExcluir() {
        Cliente selected = tableClientes.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Selecione um cliente para excluir");
            return;
        }
        try {
            // confirmação
            javafx.scene.control.Alert confirm = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Confirma exclusão");
            confirm.setContentText("Deseja excluir o cliente: " + selected.getNome() + "?");
            var res = confirm.showAndWait();
            if (res.isPresent() && res.get() == javafx.scene.control.ButtonType.OK) {
                dao.delete(selected.getId());
                loadAll();
                clearFields();
            }
        } catch (Exception e) {
            showError("Erro ao excluir cliente", e);
        }
    }

    @FXML
    private void onVoltar() {
        // fecha a janela atual
        Stage stage = (Stage) tfNome.getScene().getWindow();
        stage.close();
    }

    private void clearFields() {
        tfNome.clear();
        tfDocumento.clear();
        tfEmail.clear();
        tableClientes.getSelectionModel().clearSelection();
    }

    private void showWarning(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showError(String header, Exception e) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(header);
        a.setContentText(e.getMessage());
        a.showAndWait();
        e.printStackTrace();
    }
}

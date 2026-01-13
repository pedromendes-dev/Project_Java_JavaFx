package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.model.Cliente;
import org.example.model.Conta;
import org.example.repository.ClienteDAO;
import org.example.repository.ContaDAO;

import java.math.BigDecimal;
import java.util.List;

public class ContasController {
    @FXML private TextField tfDescricao;
    @FXML private TextField tfValor;
    @FXML private ChoiceBox<String> cbTipo;
    @FXML private ChoiceBox<Cliente> cbCliente;
    @FXML private TableView<Conta> tableContas;
    @FXML private TableColumn<Conta, Integer> colId;
    @FXML private TableColumn<Conta, String> colDescricao;
    @FXML private TableColumn<Conta, BigDecimal> colValor;
    @FXML private TableColumn<Conta, String> colTipo;

    private final ContaDAO dao = new ContaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @FXML
    public void initialize() {
        cbTipo.getItems().addAll("PAGAR", "RECEBER");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        loadClientes();
        loadAll();
    }

    private void loadClientes() {
        try {
            List<Cliente> clientes = clienteDAO.findAll();
            cbCliente.getItems().clear();
            cbCliente.getItems().addAll(clientes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAll() {
        try {
            List<Conta> list = dao.findAll();
            tableContas.getItems().clear();
            tableContas.getItems().addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onIncluir() {
        try {
            String desc = tfDescricao.getText().trim();
            if (desc.isEmpty()) { showWarning("Descrição é obrigatória"); return; }
            BigDecimal valor = new BigDecimal(tfValor.getText().trim());
            String tipo = cbTipo.getValue();
            Integer clienteId = cbCliente.getValue() != null ? cbCliente.getValue().getId() : null;
            Conta c = new Conta(desc, valor, tipo, clienteId);
            dao.insert(c);
            loadAll();
            clearFields();
        } catch (NumberFormatException nfe) {
            showWarning("Valor inválido");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAlterar() {
        Conta sel = tableContas.getSelectionModel().getSelectedItem();
        if (sel == null) { showWarning("Selecione uma conta para alterar"); return; }
        try {
            String desc = tfDescricao.getText().trim();
            if (desc.isEmpty()) { showWarning("Descrição é obrigatória"); return; }
            BigDecimal valor = new BigDecimal(tfValor.getText().trim());
            String tipo = cbTipo.getValue();
            Integer clienteId = cbCliente.getValue() != null ? cbCliente.getValue().getId() : null;
            sel.setDescricao(desc); sel.setValor(valor); sel.setTipo(tipo); sel.setClienteId(clienteId);
            dao.update(sel);
            loadAll(); clearFields();
        } catch (NumberFormatException nfe) { showWarning("Valor inválido"); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void onExcluir() {
        Conta sel = tableContas.getSelectionModel().getSelectedItem();
        if (sel == null) { showWarning("Selecione uma conta para excluir"); return; }
        try {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Confirma exclusão");
            confirm.setContentText("Deseja excluir a conta: " + sel.getDescricao() + "?");
            var res = confirm.showAndWait();
            if (res.isPresent() && res.get() == ButtonType.OK) {
                dao.delete(sel.getId()); loadAll(); clearFields();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void onVoltar() {
        Stage stage = (Stage) tfDescricao.getScene().getWindow();
        stage.close();
    }

    private void clearFields() {
        tfDescricao.clear(); tfValor.clear(); cbTipo.setValue(null); cbCliente.setValue(null);
        tableContas.getSelectionModel().clearSelection();
    }

    private void showWarning(String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}

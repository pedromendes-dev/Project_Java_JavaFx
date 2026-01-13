package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.model.Conta;
import org.example.model.Movimentacao;
import org.example.repository.ContaDAO;
import org.example.repository.MovimentacaoDAO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MovimentacaoController {
    @FXML private ChoiceBox<Conta> cbConta;
    @FXML private DatePicker dpData;
    @FXML private ChoiceBox<String> cbAcao;
    @FXML private TextField tfValor;
    @FXML private TableView<Movimentacao> tableMov;
    @FXML private TableColumn<Movimentacao, Integer> colId;
    @FXML private TableColumn<Movimentacao, String> colConta;
    @FXML private TableColumn<Movimentacao, String> colData;
    @FXML private TableColumn<Movimentacao, String> colAcao;
    @FXML private TableColumn<Movimentacao, BigDecimal> colValor;

    private final ContaDAO contaDAO = new ContaDAO();
    private final MovimentacaoDAO movDAO = new MovimentacaoDAO();

    @FXML
    public void initialize() {
        cbAcao.getItems().addAll("PAGAR","RECEBER");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colAcao.setCellValueFactory(new PropertyValueFactory<>("acao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        loadContas();
        loadAll();
    }

    private void loadContas() {
        try {
            List<Conta> contas = contaDAO.findAll();
            cbConta.getItems().clear();
            cbConta.getItems().addAll(contas);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadAll() {
        try {
            List<Movimentacao> all = movDAO.findAll();
            tableMov.getItems().clear();
            tableMov.getItems().addAll(all);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void onLancar() {
        try {
            Conta conta = cbConta.getValue();
            if (conta == null) { showWarning("Selecione a conta"); return; }
            LocalDate data = dpData.getValue();
            if (data == null) { showWarning("Informe a data"); return; }
            String acao = cbAcao.getValue();
            if (acao == null) { showWarning("Informe a ação (PAGAR/RECEBER)"); return; }
            BigDecimal valor = new BigDecimal(tfValor.getText().trim());
            Movimentacao m = new Movimentacao(conta.getId(), data, acao, valor, null);
            movDAO.insert(m);
            loadAll(); clearFields();
        } catch (NumberFormatException nfe) { showWarning("Valor inválido"); }
        catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void onVoltar() {
        Stage stage = (Stage) tfValor.getScene().getWindow();
        stage.close();
    }

    private void clearFields() { tfValor.clear(); dpData.setValue(null); cbAcao.setValue(null); cbConta.setValue(null); }

    private void showWarning(String msg) { Alert a = new Alert(Alert.AlertType.WARNING); a.setHeaderText(null); a.setContentText(msg); a.showAndWait(); }
}

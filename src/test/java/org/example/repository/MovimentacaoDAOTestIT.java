package org.example.repository;

import org.example.model.Cliente;
import org.example.model.Conta;
import org.example.model.Movimentacao;
import org.example.util.DBUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovimentacaoDAOTestIT {
    @BeforeAll
    public static void setupDatabase() throws Exception {
        try (Connection c = DBUtil.getConnection(); Statement st = c.createStatement()) {
            InputStream in = MovimentacaoDAOTestIT.class.getResourceAsStream("/db/schema_mysql.sql");
            assertNotNull(in);
            String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            sql = sql.replaceAll("(?i)CREATE DATABASE.*?;"," ");
            sql = sql.replaceAll("(?i)USE .*?;"," ");
            // adaptar para H2: remover ENGINE, converter ENUM para VARCHAR
            sql = sql.replaceAll("ENGINE=\\w+", "");
            sql = sql.replaceAll("ENUM\\(\'PAGAR\'\\s*,\\s*\'RECEBER\'\\)", "VARCHAR(10)");
            String[] parts = sql.split(";");
            for (String p : parts) { String t = p.trim(); if (!t.isEmpty()) st.execute(t); }
        }
    }

    @Test
    public void testLancarMovimentacao() throws Exception {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente c = new Cliente("Cliente Mov", null, null);
        clienteDAO.insert(c);

        ContaDAO contaDAO = new ContaDAO();
        Conta conta = new Conta("Conta Mov", new BigDecimal("100.00"), "RECEBER", c.getId());
        contaDAO.insert(conta);

        MovimentacaoDAO movDAO = new MovimentacaoDAO();
        Movimentacao m = new Movimentacao(conta.getId(), LocalDate.now(), "RECEBER", new BigDecimal("50.00"), "Teste");
        Movimentacao saved = movDAO.insert(m);
        assertNotNull(saved.getId());

        List<Movimentacao> all = movDAO.findAll();
        assertTrue(all.size() >= 1);
    }
}

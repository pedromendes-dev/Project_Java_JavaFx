package org.example.repository;

import org.example.model.Conta;
import org.example.model.Cliente;
import org.example.util.DBUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContaDAOTestIT {
    @BeforeAll
    public static void setupDatabase() throws Exception {
        try (Connection c = DBUtil.getConnection(); Statement st = c.createStatement()) {
            InputStream in = ContaDAOTestIT.class.getResourceAsStream("/db/schema_mysql.sql");
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
    public void testCrudConta() throws Exception {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente c = new Cliente("Cliente Conta", null, null);
        clienteDAO.insert(c);

        ContaDAO dao = new ContaDAO();
        Conta conta = new Conta("Teste", new BigDecimal("123.45"), "PAGAR", c.getId());
        Conta saved = dao.insert(conta);
        assertNotNull(saved.getId());

        Conta fetched = dao.findById(saved.getId());
        assertEquals("Teste", fetched.getDescricao());

        saved.setDescricao("Alterado"); dao.update(saved);
        Conta updated = dao.findById(saved.getId());
        assertEquals("Alterado", updated.getDescricao());

        List<Conta> all = dao.findAll(); assertTrue(all.size() >= 1);

        dao.delete(saved.getId()); assertNull(dao.findById(saved.getId()));
    }
}


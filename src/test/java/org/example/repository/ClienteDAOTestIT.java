package org.example.repository;

import org.example.model.Cliente;
import org.example.util.DBUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteDAOTestIT {

    @BeforeAll
    public static void setupDatabase() throws Exception {
        try (Connection c = DBUtil.getConnection(); Statement st = c.createStatement()) {
            InputStream in = ClienteDAOTestIT.class.getResourceAsStream("/db/schema_mysql.sql");
            assertNotNull(in, "schema_mysql.sql não encontrado em resources/db");
            String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            sql = sql.replaceAll("(?i)CREATE DATABASE.*?;"," ");
            sql = sql.replaceAll("(?i)USE .*?;"," ");
            // adaptar para H2: remover ENGINE, converter ENUM para VARCHAR
            sql = sql.replaceAll("ENGINE=\\w+", "");
            sql = sql.replaceAll("ENUM\\(\'PAGAR\'\\s*,\\s*\'RECEBER\'\\)", "VARCHAR(10)");
            String[] parts = sql.split(";");
            for (String p : parts) {
                String t = p.trim();
                if (!t.isEmpty()) st.execute(t);
            }
        }
    }

    @Test
    public void testCrudCliente() throws Exception {
        ClienteDAO dao = new ClienteDAO();
        Cliente c = new Cliente("Test User", "000.000.000-00", "test@example.com");
        Cliente saved = dao.insert(c);
        assertNotNull(saved.getId());

        Cliente fetched = dao.findById(saved.getId());
        assertEquals("Test User", fetched.getNome());

        saved.setNome("Updated User");
        dao.update(saved);
        Cliente updated = dao.findById(saved.getId());
        assertEquals("Updated User", updated.getNome());

        List<Cliente> all = dao.findAll();
        assertTrue(all.size() >= 1);

        dao.delete(saved.getId());
        Cliente removed = dao.findById(saved.getId());
        assertNull(removed);
    }
}


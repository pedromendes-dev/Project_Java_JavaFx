package org.example.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class DBConnectionTest {
    public static void main(String[] args) {
        System.out.println("Iniciando teste de conexão ao banco...");
        try (Connection conn = DBUtil.getConnection()) {
            DatabaseMetaData md = conn.getMetaData();
            System.out.println("Conectado com sucesso!");
            System.out.println("URL: " + md.getURL());
            System.out.println("Banco: " + md.getDatabaseProductName() + " " + md.getDatabaseProductVersion());
            System.out.println("Usuário: " + md.getUserName());
        } catch (Exception e) {
            System.out.println("Falha ao conectar: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}


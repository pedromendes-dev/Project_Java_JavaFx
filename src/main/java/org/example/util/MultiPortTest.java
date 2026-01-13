package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MultiPortTest {
    public static void main(String[] args) {
        String user = "root";
        String pass = "root";
        String host = "127.0.0.1";
        int[] ports = {3306, 3308};

        for (int port : ports) {
            String url = String.format("jdbc:mysql://%s:%d/pag_rec?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", host, port);
            System.out.println("\nTestando URL: " + url);
            try (Connection c = DriverManager.getConnection(url, user, pass)) {
                System.out.println("Conexão OK na porta: " + port + ", DB: " + c.getMetaData().getDatabaseProductName() + " " + c.getMetaData().getDatabaseProductVersion());
            } catch (SQLException e) {
                System.out.println("Falha na porta " + port + ": " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }
    }
}


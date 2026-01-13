package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DBUtil {
    private static String url;
    private static String user;
    private static String password;

    static {
        // 1) Prioriza propriedades de sistema (permitir override em testes ou CI)
        String sysUrl = System.getProperty("db.url");
        String sysUser = System.getProperty("db.user");
        String sysPass = System.getProperty("db.password");

        if (sysUrl != null && !sysUrl.isBlank()) {
            // se a URL foi informada via propriedade do sistema, usa-a
            url = sysUrl;
            user = sysUser != null ? sysUser : "";
            password = sysPass != null ? sysPass : "";
        } else {
            // 2) Tenta carregar application.properties do classpath (test resources podem sobrepor main)
            try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
                Properties props = new Properties();
                if (in != null) {
                    props.load(in);
                    // se existir db.url usa diretamente (útil para testes com H2)
                    String configuredUrl = props.getProperty("db.url");
                    if (configuredUrl != null && !configuredUrl.isBlank()) {
                        url = configuredUrl;
                        user = props.getProperty("db.user", "");
                        password = props.getProperty("db.password", "");
                    } else {
                        String host = props.getProperty("db.host", "localhost");
                        String port = props.getProperty("db.port", "3306");
                        String database = props.getProperty("db.name", "pag_rec");
                        user = props.getProperty("db.user", "root");
                        password = props.getProperty("db.password", "");
                        url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", host, port, database);
                    }
                } else {
                    // valores padrão
                    url = "jdbc:mysql://localhost:3306/pag_rec?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                    user = "root";
                    password = "root";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        // tentar carregar driver explicitamente para evitar problemas de auto-registro em alguns ambientes
        try {
            if (url != null) {
                if (url.startsWith("jdbc:h2:")) {
                    try { Class.forName("org.h2.Driver"); } catch (ClassNotFoundException ignored) {}
                } else if (url.startsWith("jdbc:mysql:")) {
                    try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException ignored) {}
                }
            }
        } catch (Throwable t) {
            // ignore
        }
        return DriverManager.getConnection(url, user, password);
    }
}

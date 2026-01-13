package org.example.util;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DBUtilTest {
    @Test
    public void testConnection() throws Exception {
        try (Connection c = DBUtil.getConnection()) {
            assertNotNull(c);
            assertFalse(c.isClosed());
        }
    }
}


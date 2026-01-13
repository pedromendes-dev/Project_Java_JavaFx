package org.example.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilTest {
    @Test
    public void testIsNotEmpty() {
        assertTrue(ValidationUtil.isNotEmpty("abc"));
        assertFalse(ValidationUtil.isNotEmpty("   "));
        assertFalse(ValidationUtil.isNotEmpty(null));
    }

    @Test
    public void testEmail() {
        assertTrue(ValidationUtil.isValidEmail(null));
        assertTrue(ValidationUtil.isValidEmail(""));
        assertTrue(ValidationUtil.isValidEmail("a@b.com"));
        assertFalse(ValidationUtil.isValidEmail("not-an-email"));
    }

    @Test
    public void testDocument() {
        assertTrue(ValidationUtil.isValidDocument(null));
        assertTrue(ValidationUtil.isValidDocument(""));
        assertTrue(ValidationUtil.isValidDocument("12345678901")); // CPF 11
        assertTrue(ValidationUtil.isValidDocument("12345678901234")); // CNPJ 14
        assertFalse(ValidationUtil.isValidDocument("12"));
    }

    @Test
    public void testParseBigDecimalPositive() {
        assertEquals(new BigDecimal("123.45"), ValidationUtil.parseBigDecimalPositive("123.45"));
        assertEquals(new BigDecimal("1000"), ValidationUtil.parseBigDecimalPositive("1,000".replace(",","")));
        assertThrows(NumberFormatException.class, () -> ValidationUtil.parseBigDecimalPositive("-1"));
    }
}


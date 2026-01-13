package org.example.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class ValidationUtil {
    private static final Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern DIGITS = Pattern.compile("\\d+");

    public static boolean isNotEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        if (!isNotEmpty(email)) return true; // optional field — if empty, it's allowed
        return EMAIL.matcher(email).matches();
    }

    public static boolean isValidDocument(String doc) {
        if (!isNotEmpty(doc)) return true; // optional field
        String only = doc.replaceAll("\\D", "");
        // accept CPF (11 digits) or CNPJ (14 digits) or shorter id-like
        return only.length() == 11 || only.length() == 14 || (only.length() >= 6 && only.length() <= 20);
    }

    public static BigDecimal parseBigDecimalPositive(String s) throws NumberFormatException {
        if (s == null) throw new NumberFormatException("null");
        String sanitized = s.replaceAll("[\\s\\$,]", "");
        BigDecimal v = new BigDecimal(sanitized);
        if (v.compareTo(BigDecimal.ZERO) < 0) throw new NumberFormatException("negative");
        return v;
    }
}


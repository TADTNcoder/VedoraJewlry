package com.jewelry.jewelryshopbackend.service.auth;

import com.jewelry.jewelryshopbackend.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthInputSanitizerTest {

    private final AuthInputSanitizer sanitizer = new AuthInputSanitizer();

    @Test
    void normalizeEmail_shouldTrimAndLowercase() {
        String result = sanitizer.normalizeEmail("  TeSt@Example.COM ");
        assertEquals("test@example.com", result);
    }

    @Test
    void normalizeEmail_shouldThrowWhenBlank() {
        assertThrows(BadRequestException.class, () -> sanitizer.normalizeEmail("   "));
    }

    @Test
    void normalizeFullName_shouldCollapseMultipleSpaces() {
        String result = sanitizer.normalizeFullName("  Nguyen   Van   A ");
        assertEquals("Nguyen Van A", result);
    }

    @Test
    void normalizeFullName_shouldThrowWhenBlank() {
        assertThrows(BadRequestException.class, () -> sanitizer.normalizeFullName("   "));
    }
}

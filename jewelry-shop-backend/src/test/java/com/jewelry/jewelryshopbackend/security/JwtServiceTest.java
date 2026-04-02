package com.jewelry.jewelryshopbackend.security;

import com.jewelry.jewelryshopbackend.entity.User;
import com.jewelry.jewelryshopbackend.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceTest {

    @Test
    void validateConfiguration_shouldThrowWhenSecretTooShort() {
        JwtService jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", "short");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1000L);

        assertThrows(IllegalStateException.class, () -> ReflectionTestUtils.invokeMethod(jwtService, "validateConfiguration"));
    }

    @Test
    void generateAndValidateToken_shouldWork() {
        JwtService jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", "12345678901234567890123456789012");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 60_000L);
        ReflectionTestUtils.invokeMethod(jwtService, "validateConfiguration");

        User user = User.builder()
                .email("user@example.com")
                .password("encoded")
                .status(UserStatus.ACTIVE)
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
        assertEquals("user@example.com", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, userDetails));

        User another = User.builder()
                .email("another@example.com")
                .password("encoded")
                .status(UserStatus.ACTIVE)
                .build();
        assertFalse(jwtService.isTokenValid(token, new CustomUserDetails(another)));
    }
}

package com.jewelry.jewelryshopbackend.controller.public_api;

import com.jewelry.jewelryshopbackend.dto.request.auth.LoginRequest;
import com.jewelry.jewelryshopbackend.dto.request.auth.RegisterRequest;
import com.jewelry.jewelryshopbackend.dto.response.auth.AuthResponse;
import com.jewelry.jewelryshopbackend.payload.ApiResponse;
import com.jewelry.jewelryshopbackend.service.auth.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void register_shouldReturnCreatedResponse() {
        RegisterRequest request = new RegisterRequest();
        request.setFullName("Test User");
        request.setEmail("test@example.com");
        request.setPassword("Password1");

        AuthResponse authResponse = AuthResponse.builder()
                .token("token")
                .tokenType("Bearer")
                .userId(1L)
                .fullName("Test User")
                .email("test@example.com")
                .roles(List.of("ROLE_USER"))
                .build();
        when(authService.register(request)).thenReturn(authResponse);

        ResponseEntity<ApiResponse<AuthResponse>> response = authController.register(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals("token", response.getBody().getData().getToken());
    }

    @Test
    void login_shouldReturnOkResponse() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("Password1");

        AuthResponse authResponse = AuthResponse.builder()
                .token("token")
                .tokenType("Bearer")
                .userId(1L)
                .fullName("Test User")
                .email("test@example.com")
                .roles(List.of("ROLE_USER"))
                .build();
        when(authService.login(request)).thenReturn(authResponse);

        ResponseEntity<ApiResponse<AuthResponse>> response = authController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().isSuccess());
        assertEquals("token", response.getBody().getData().getToken());
    }
}

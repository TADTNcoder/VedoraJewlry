package com.jewelry.jewelryshopbackend.service.auth.impl;

import com.jewelry.jewelryshopbackend.dto.request.auth.LoginRequest;
import com.jewelry.jewelryshopbackend.dto.request.auth.RegisterRequest;
import com.jewelry.jewelryshopbackend.dto.response.auth.AuthResponse;
import com.jewelry.jewelryshopbackend.entity.Role;
import com.jewelry.jewelryshopbackend.entity.User;
import com.jewelry.jewelryshopbackend.entity.UserRole;
import com.jewelry.jewelryshopbackend.enums.UserStatus;
import com.jewelry.jewelryshopbackend.exception.UnauthorizedException;
import com.jewelry.jewelryshopbackend.repository.RoleRepository;
import com.jewelry.jewelryshopbackend.repository.UserRepository;
import com.jewelry.jewelryshopbackend.security.JwtService;
import com.jewelry.jewelryshopbackend.service.auth.AuthInputSanitizer;
import com.jewelry.jewelryshopbackend.service.auth.AuthLoginAttemptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthInputSanitizer authInputSanitizer;

    @Mock
    private AuthLoginAttemptService authLoginAttemptService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void register_shouldReturnTokenAndUserData() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("TEST@EXAMPLE.COM");
        request.setFullName("  Test User ");
        request.setPassword("Password1");

        Role role = Role.builder().name("ROLE_USER").build();
        when(authInputSanitizer.normalizeEmail(request.getEmail())).thenReturn("test@example.com");
        when(authInputSanitizer.normalizeFullName(request.getFullName())).thenReturn("Test User");
        when(userRepository.existsByEmailIgnoreCase("test@example.com")).thenReturn(false);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("Password1")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });
        when(jwtService.generateToken(any())).thenReturn("jwt-token");

        AuthResponse response = authService.register(request);

        assertEquals("jwt-token", response.getToken());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("Test User", response.getFullName());
        assertEquals(1, response.getRoles().size());
        assertEquals("ROLE_USER", response.getRoles().get(0));
    }

    @Test
    void login_shouldRecordFailureWhenBadCredentials() {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@example.com");
        request.setPassword("wrong");

        when(authInputSanitizer.normalizeEmail("user@example.com")).thenReturn("user@example.com");
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("bad"));

        assertThrows(UnauthorizedException.class, () -> authService.login(request));
        verify(authLoginAttemptService).recordFailure("user@example.com");
    }

    @Test
    void login_shouldReturnTokenWhenSuccess() {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@example.com");
        request.setPassword("Password1");

        User user = activeUser("user@example.com");
        when(authInputSanitizer.normalizeEmail("user@example.com")).thenReturn("user@example.com");
        when(userRepository.findByEmailIgnoreCase("user@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("token-success");

        AuthResponse response = authService.login(request);

        verify(authLoginAttemptService).recordSuccess("user@example.com");
        assertEquals("token-success", response.getToken());
        assertEquals("user@example.com", response.getEmail());
    }

    private User activeUser(String email) {
        Role role = Role.builder().name("ROLE_USER").build();
        User user = User.builder()
                .fullName("User")
                .email(email)
                .password("encoded")
                .status(UserStatus.ACTIVE)
                .build();
        user.setId(1L);
        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role)
                .build();
        user.getUserRoles().add(userRole);
        return user;
    }
}

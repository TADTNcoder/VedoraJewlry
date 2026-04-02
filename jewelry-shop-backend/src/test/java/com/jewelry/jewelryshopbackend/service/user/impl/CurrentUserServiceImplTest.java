package com.jewelry.jewelryshopbackend.service.user.impl;

import com.jewelry.jewelryshopbackend.entity.User;
import com.jewelry.jewelryshopbackend.enums.UserStatus;
import com.jewelry.jewelryshopbackend.exception.UnauthorizedException;
import com.jewelry.jewelryshopbackend.repository.UserRepository;
import com.jewelry.jewelryshopbackend.security.CustomUserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrentUserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CurrentUserServiceImpl currentUserService;

    @AfterEach
    void cleanUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void requireCurrentUser_shouldReturnUserFromPrincipalWhenCustomUserDetails() {
        User user = buildUser("principal@example.com");
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User result = currentUserService.requireCurrentUser();
        assertEquals(user, result);
    }

    @Test
    void requireCurrentUser_shouldLoadFromRepositoryWhenPrincipalIsString() {
        User user = buildUser("repo@example.com");
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("repo@example.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userRepository.findByEmailIgnoreCase("repo@example.com")).thenReturn(Optional.of(user));

        User result = currentUserService.requireCurrentUser();
        assertEquals(user, result);
    }

    @Test
    void requireCurrentUser_shouldThrowWhenNoAuthentication() {
        SecurityContextHolder.clearContext();
        assertThrows(UnauthorizedException.class, () -> currentUserService.requireCurrentUser());
    }

    private User buildUser(String email) {
        return User.builder()
                .fullName("User")
                .email(email)
                .password("encoded")
                .status(UserStatus.ACTIVE)
                .build();
    }
}

package com.jewelry.jewelryshopbackend.mapper;

import com.jewelry.jewelryshopbackend.dto.response.user.UserProfileResponse;
import com.jewelry.jewelryshopbackend.entity.Role;
import com.jewelry.jewelryshopbackend.entity.User;
import com.jewelry.jewelryshopbackend.entity.UserRole;
import com.jewelry.jewelryshopbackend.enums.UserStatus;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void toProfileResponse_shouldSortAndDistinctRoles() {
        Role admin = Role.builder().name("ROLE_ADMIN").build();
        Role userRole = Role.builder().name("ROLE_USER").build();
        User user = User.builder()
                .fullName("User")
                .email("u@example.com")
                .status(UserStatus.ACTIVE)
                .build();
        user.setId(1L);
        user.setUserRoles(Set.of(
                UserRole.builder().user(user).role(userRole).build(),
                UserRole.builder().user(user).role(admin).build(),
                UserRole.builder().user(user).role(userRole).build()
        ));

        UserProfileResponse response = userMapper.toProfileResponse(user);
        assertEquals(2, response.getRoles().size());
        assertEquals("ROLE_ADMIN", response.getRoles().get(0));
        assertEquals("ROLE_USER", response.getRoles().get(1));
    }
}

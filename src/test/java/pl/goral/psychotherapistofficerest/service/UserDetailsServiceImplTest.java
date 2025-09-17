package pl.goral.psychotherapistofficerest.service;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.goral.psychotherapistofficerest.dto.UserDto;
import pl.goral.psychotherapistofficerest.model.AppUser;
import pl.goral.psychotherapistofficerest.model.UserRole;
import pl.goral.psychotherapistofficerest.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserDetailsServiceImplIntegrationTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    private AppUser savedUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        // Zakładam, że AppUser ma pola: id, email, password, itp.
        savedUser = AppUser.builder()
                .email("test@example.com")
                .password("password123")
                .build();
        savedUser = userRepository.save(savedUser);
    }

    @Test
    void shouldLoadUserByUsername_whenExists() {
        var userDetails = userDetailsService.loadUserByUsername("test@example.com");
        assertThat(userDetails.getUsername()).isEqualTo("test@example.com");
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("notfound@example.com"))
                .isInstanceOf(org.springframework.security.core.userdetails.UsernameNotFoundException.class);
    }

    @Test
    void shouldGetAllUsers() {
        List<UserDto> users = userDetailsService.getAllUsers();
        assertThat(users).extracting(UserDto::getEmail).contains("test@example.com");
    }

    @Test
    void shouldGetUserById() {
        UserDto dto = userDetailsService.getUserById(savedUser.getId());
        assertThat(dto.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void shouldGetUserByEmail() {
        UserDto dto = userDetailsService.getUserByEmail("test@example.com");
        assertThat(dto.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @Disabled("Not yet implemented")
    void shouldDeleteUser() {
        userDetailsService.deleteUser(savedUser.getId());
        assertThat(userRepository.existsById(savedUser.getId())).isFalse();
    }

    @Test
    @Disabled("Not yet implemented")
    void shouldThrowWhenDeletingNotExistingUser() {
        assertThatThrownBy(() -> userDetailsService.deleteUser(12345L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }
}
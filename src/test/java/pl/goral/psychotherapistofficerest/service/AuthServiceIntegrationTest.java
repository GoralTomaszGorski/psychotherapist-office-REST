package pl.goral.psychotherapistofficerest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import pl.goral.psychotherapistofficerest.dto.request.RegisterRequestDto;
import pl.goral.psychotherapistofficerest.dto.response.AuthResponseDto;
import pl.goral.psychotherapistofficerest.model.AppUser;
import pl.goral.psychotherapistofficerest.model.UserRole;
import pl.goral.psychotherapistofficerest.repository.UserRepository;
import pl.goral.psychotherapistofficerest.repository.UserRoleRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("dev") //h2
class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
        userRoleRepository.save(UserRole.builder().name("ROLE_USER").description("Default user role").build());
    }

    @Test
    void shouldRegisterNewUser() {
        RegisterRequestDto req = RegisterRequestDto.builder()
                .email("newuser@example.com")
                .password("password123")
                .build();

        AuthResponseDto response = authService.register(req);

        assertThat(response.getEmail()).isEqualTo("newuser@example.com");
        assertThat(userRepository.existsByEmail("newuser@example.com")).isTrue();
    }

    @Test
    void shouldThrowWhenRegisteringWithExistingEmail() {
        AppUser user = AppUser.builder().email("existing@example.com").password(passwordEncoder.encode("pass")).build();
        userRepository.save(user);

        RegisterRequestDto req = RegisterRequestDto.builder()
                .email("existing@example.com")
                .password("password123")
                .build();

        assertThatThrownBy(() -> authService.register(req))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void shouldLoginExistingUser() {
        UserRole userRole = userRoleRepository.findByName("ROLE_USER").orElseThrow();
        AppUser user = AppUser.builder()
                .email("login@example.com")
                .password(passwordEncoder.encode("password123"))
                .roles(java.util.Set.of(userRole))
                .build();
        userRepository.save(user);

        AuthResponseDto response = authService.login("login@example.com", "password123");

        assertThat(response.getEmail()).isEqualTo("login@example.com");
        assertThat(response.getToken()).isNotBlank();
    }

    @Test
    void shouldThrowWhenLoginWithWrongCredentials() {
        UserRole userRole = userRoleRepository.findByName("ROLE_USER").orElseThrow();
        AppUser user = AppUser.builder()
                .email("wronglogin@example.com")
                .password(passwordEncoder.encode("password123"))
                .roles(java.util.Set.of(userRole))
                .build();
        userRepository.save(user);

        assertThatThrownBy(() -> authService.login("wronglogin@example.com", "wrongPassword"))
                .isInstanceOf(org.springframework.security.authentication.BadCredentialsException.class)
                .hasMessageContaining("Bad credentials");
    }

}
package pl.goral.psychotherapistofficerest.service;

import java.util.HashSet;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.goral.psychotherapistofficerest.config.JwtService;
import pl.goral.psychotherapistofficerest.model.AppUser;
import pl.goral.psychotherapistofficerest.model.UserRole;
import pl.goral.psychotherapistofficerest.repository.UserRepository;
import pl.goral.psychotherapistofficerest.repository.UserRoleRepository;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Rejestracja nowego użytkownika
     */
    public void register(AppUser user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User with email already exists");
        }

        // domyślna rola USER
        UserRole userRole = userRoleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default role USER not found"));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>() {
            {
                add(userRole);
            }
        });

        userRepository.save(user);
    }

    /**
     * Logowanie i generowanie JWT
     */
    public String login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtService.generateToken(user);
    }
}
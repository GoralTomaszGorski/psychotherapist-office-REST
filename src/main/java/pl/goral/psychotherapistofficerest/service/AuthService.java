package pl.goral.psychotherapistofficerest.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.goral.psychotherapistofficerest.config.jwt.JwtService;
import pl.goral.psychotherapistofficerest.dto.UserDto;
import pl.goral.psychotherapistofficerest.dto.request.RegisterRequestDto;
import pl.goral.psychotherapistofficerest.dto.response.AuthResponseDto;
import pl.goral.psychotherapistofficerest.mapper.UserMapper;
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

    public AuthResponseDto register(RegisterRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("User already exists");
        }
        AppUser user = UserMapper.fromRegisterDto(dto, passwordEncoder);

        UserRole role = userRoleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role USER not found"));
        user.setRoles(Set.of(role));

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponseDto(token, user.getEmail(), user.getId());
    }

    public AuthResponseDto login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);
        return new AuthResponseDto(token, user.getEmail(), user.getId());
    }

    public UserDto createUser(UserDto dto) {
        AppUser user = UserMapper.toEntity(dto, passwordEncoder);
        AppUser saved = userRepository.save(user);
        return UserMapper.toDto(saved);
    }
}
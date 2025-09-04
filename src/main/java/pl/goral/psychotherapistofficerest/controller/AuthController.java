package pl.goral.psychotherapistofficerest.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.goral.psychotherapistofficerest.dto.request.LoginRequestDto;
import pl.goral.psychotherapistofficerest.dto.request.RegisterRequestDto;
import pl.goral.psychotherapistofficerest.dto.response.AuthResponseDto;
import pl.goral.psychotherapistofficerest.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterRequestDto> register(@Valid @RequestBody RegisterRequestDto request) {
        authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponseDto("User registered successfully", null));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponseDto("Login successful", token));
    }
}

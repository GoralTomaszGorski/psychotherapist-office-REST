package pl.goral.psychotherapistofficerest.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import pl.goral.psychotherapistofficerest.dto.request.LoginRequestDto;
import pl.goral.psychotherapistofficerest.dto.request.RegisterRequestDto;
import pl.goral.psychotherapistofficerest.dto.response.AuthResponseDto;
import pl.goral.psychotherapistofficerest.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with provided email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto request) {
        authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthResponseDto("User registered successfully", null));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, token returned"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponseDto("Login successful", token));
    }
}

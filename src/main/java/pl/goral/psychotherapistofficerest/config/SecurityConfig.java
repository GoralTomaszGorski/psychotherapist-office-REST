package pl.goral.psychotherapistofficerest.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pl.goral.psychotherapistofficerest.config.jwt.JwtAuthFilter;
import pl.goral.psychotherapistofficerest.config.jwt.JwtService;
import pl.goral.psychotherapistofficerest.service.UserDetailsServiceImpl;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtService, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 🔒 Wyłączamy CSRF dla REST API (JWT i tak chroni)
                .csrf(AbstractHttpConfigurer::disable)

                // 🔒 Stateless – bo używasz JWT, a nie sesji
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 🔒 Pozwala na dostęp do konsoli H2 i swaggera w iframe
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                // 🔒 Konfiguracja autoryzacji
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/api/v1/docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/api/swagger-ui.html",
                                "/api/swagger-ui/**",
                                "/h2-console/**",
                                "/api/auth/**",
                                "/api/index/**",
                                "/api/v1/swagger/**"
                        ).permitAll()

                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/user/**").hasRole("USER")

                        // na czas deva permitAll dla całego /api/**
                        // ⚠️ po wdrożeniu usuń to
                        .requestMatchers("/api/**").permitAll()

                        // reszta wymaga autoryzacji
                        .anyRequest().authenticated()
                )

                // 🔒 Dodajemy nasz JWT filter przed UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

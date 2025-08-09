package pl.goral.psychotherapistofficerest.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.goral.psychotherapistofficerest.model.UserRole;
import pl.goral.psychotherapistofficerest.repository.UserRepository;
import pl.goral.psychotherapistofficerest.repository.UserRoleRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String DEFAULT_USER_ROLE = "USER";
    private final UserRepository userRepository;
//    private final ChangePasswordTokenRepository changePasswordTokenRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

//    public Optional<UserCredentialsDto> findCredentialsByEmail(String email) {
//        return userRepository.findByEmail(email)
//                .map(UserCredentialsDtoMapper::map);
//    }

//    @Transactional
//    public void registerUserWithDefaultRole(
//            UserRegistrationDto userRegistration) {
//        UserRole defaultRole = userRoleRepository.findByName(DEFAULT_USER_ROLE).orElseThrow();
//
//        User user = new User();
//        user.setEmail(userRegistration
//                .getEmail());
//        user.setPassword(passwordEncoder
//                .encode(userRegistration.getPassword()));
//        user.getRoles().add(defaultRole);
//        userRepository.save(user);
//    }

//    public String generateResetToken(User user) {
//        UUID uuid = UUID.randomUUID();
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(30);
//        ChangePasswordToken resetToken = new ChangePasswordToken();
//        resetToken.setUser(user);
//        resetToken.setToken(uuid.toString());
//        resetToken.setExpiryDateTime(expiryDateTime);
//        changePasswordTokenRepository.save(resetToken);
////        String endpointUrl = "https://psycholog-krasnik.pl/reset-password";
//        String endpointUrl = "http://localhost:8080/reset-password";
//
//        return endpointUrl + "/" + resetToken.getToken();
//    }

    public String getCurrentUserName() {
        String currentUsername = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return currentUsername;
    }

    public boolean hasExipred(LocalDateTime expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(currentDateTime);
    }
}
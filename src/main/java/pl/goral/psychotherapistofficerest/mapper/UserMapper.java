package pl.goral.psychotherapistofficerest.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.goral.psychotherapistofficerest.dto.UserDto;
import pl.goral.psychotherapistofficerest.dto.request.RegisterRequestDto;
import pl.goral.psychotherapistofficerest.model.AppUser;
import pl.goral.psychotherapistofficerest.model.UserRole;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static UserDto toDto(AppUser user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(
                        user.getRoles().stream()
                                .map(UserRole::getName)
                                .collect(Collectors.toSet())
                )
                .build();
    }

    public static AppUser toEntity(UserDto dto, PasswordEncoder passwordEncoder) {
        return AppUser.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(passwordEncoder.encode("defaultPassword")) // do mapowania DTO → entity (np. admin)
                .roles(dto.getRoles().stream()
                        .map(roleName -> UserRole.builder().name(roleName).build())
                        .collect(Collectors.toSet()))
                .build();
    }

    public static AppUser fromRegisterDto(RegisterRequestDto dto, PasswordEncoder passwordEncoder) {
        return AppUser.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Set.of()) // role will be add in AuthService
                .build();
    }

}

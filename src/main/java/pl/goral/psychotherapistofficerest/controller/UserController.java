package pl.goral.psychotherapistofficerest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import pl.goral.psychotherapistofficerest.dto.UserDto;
import pl.goral.psychotherapistofficerest.model.AppUser;
import pl.goral.psychotherapistofficerest.service.UserDetailsServiceImpl;


import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.Hibernate.map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserDetailsServiceImpl userService;

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(user -> new UserDto(
                        user.getId(), 
                        user.getEmail(),
                        user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet())))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}

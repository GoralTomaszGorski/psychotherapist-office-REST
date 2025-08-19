//package pl.goral.psychotherapistofficerest.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.web.bind.annotation.*;
//import pl.goral.psychotherapistofficerest.config.UserDetailsServiceImpl;
//import pl.goral.psychotherapistofficerest.repository.UserRepository;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserRepository userRepository;
//    private final UserDetailsServiceImpl userDetailsService;
//
//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        user.setEmail(updatedUser.getEmail());
//        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
//        user.setRoles(updatedUser.getRoles());
//        userRepository.save(user);
//        return ResponseEntity.ok(user);
//    }
//}

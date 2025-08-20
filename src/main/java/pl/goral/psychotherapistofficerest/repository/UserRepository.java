package pl.goral.psychotherapistofficerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.goral.psychotherapistofficerest.model.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}

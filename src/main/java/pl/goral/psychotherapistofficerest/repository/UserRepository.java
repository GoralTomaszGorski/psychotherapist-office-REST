package pl.goral.psychotherapistofficerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.goral.psychotherapistofficerest.model.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    
    @Override
    default boolean existsById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }

    boolean existsByEmail(String email);
    

}

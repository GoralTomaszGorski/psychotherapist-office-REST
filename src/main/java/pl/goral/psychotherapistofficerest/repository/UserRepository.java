package pl.goral.psychotherapistofficerest.repository;

import org.springframework.data.repository.CrudRepository;
import pl.goral.psychotherapistofficerest.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findUsersByEmail(String email);

}

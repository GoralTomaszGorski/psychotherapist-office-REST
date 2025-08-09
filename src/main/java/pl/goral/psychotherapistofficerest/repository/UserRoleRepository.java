package pl.goral.psychotherapistofficerest.repository;

import org.springframework.data.repository.CrudRepository;
import pl.goral.psychotherapistofficerest.model.UserRole;

import java.util.Optional;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    Optional<UserRole> findByName(String name);
}
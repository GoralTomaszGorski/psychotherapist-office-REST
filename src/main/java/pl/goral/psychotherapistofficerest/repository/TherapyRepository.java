package pl.goral.psychotherapistofficerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.goral.psychotherapistofficerest.model.Therapy;

import java.util.Optional;

public interface TherapyRepository extends JpaRepository<Therapy, Long> {

    Optional<Therapy> findByKindOfTherapy(String kindOfTherapy);


}

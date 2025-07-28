package pl.goral.psychotherapistofficerest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.goral.psychotherapistofficerest.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}

package pl.goral.psychotherapistofficerest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.goral.psychotherapistofficerest.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Override
    List<Patient> findAll();

    Optional<Patient> findPatientById(Long id);


}

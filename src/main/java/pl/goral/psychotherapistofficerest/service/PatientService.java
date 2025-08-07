package pl.goral.psychotherapistofficerest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.goral.psychotherapistofficerest.dto.request.PatientRequestDTO;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDTO;
import pl.goral.psychotherapistofficerest.mapper.PatientMapper;
import pl.goral.psychotherapistofficerest.model.Patient;
import pl.goral.psychotherapistofficerest.repository.PatientRepository;
import pl.goral.psychotherapistofficerest.utils.DateInWarsaw;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final DateInWarsaw dateInWarsaw;
    private final PatientMapper patientMapper;

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> findPatientById(long id) {
        return patientRepository.findById(id);
    }

    public PatientResponseDTO createPatient(PatientRequestDTO request) {
        Patient patient = patientMapper.toEntity(request);
        patient.setJoinDate(dateInWarsaw.getLocalDateInWarsaw());
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toResponseDTO(savedPatient);
    }
}

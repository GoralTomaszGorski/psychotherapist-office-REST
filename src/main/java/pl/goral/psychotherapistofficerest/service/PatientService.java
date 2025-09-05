package pl.goral.psychotherapistofficerest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.goral.psychotherapistofficerest.dto.request.PatientRequestDTO;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDTO;
import pl.goral.psychotherapistofficerest.mapper.PatientMapper;
import pl.goral.psychotherapistofficerest.model.Patient;
import pl.goral.psychotherapistofficerest.repository.PatientRepository;
import pl.goral.psychotherapistofficerest.utils.DateInWarsaw;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final DateInWarsaw dateInWarsaw;
    private final PatientMapper patientMapper;

    public List<PatientResponseDTO> findAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::toResponseDTO)
                .toList();
    }

    public Optional<PatientResponseDTO> findPatientById(long id) {
                return patientRepository.findById(id)
                        .map(patientMapper::toResponseDTO);
    }

    public PatientResponseDTO createPatient(PatientRequestDTO request) {
        Patient patient = patientMapper.toEntity(request);
        if (request.nick() == null || request.nick().isBlank()) {
            patient.setNick(generateNick(patient.getName(), patient.getSurname(), request.telephone()));
        } else {
            patient.setNick(request.nick());
        }
        patient.setJoinDate(dateInWarsaw.getLocalDateInWarsaw());
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toResponseDTO(savedPatient);
    }

    private String generateNick(String name, String surname, String telephone) {
        // Założenie: name, surname min. 2 znaki, telephone min. 3 cyfry (walidowane wcześniej)
        String last3digits = telephone.substring(Math.max(0, telephone.length() - 3));
        // Jeśli nazwisko jest krótsze niż 2 litery, bierzemy tyle ile się da
        String surnamePart = surname.length() >= 2 ? surname.substring(0, 2) : surname;
        String namePart = name.isEmpty() ? "" : String.valueOf(name.charAt(0));
        return last3digits + namePart + surnamePart;
    }
}

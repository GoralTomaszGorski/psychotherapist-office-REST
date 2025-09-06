package pl.goral.psychotherapistofficerest.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.goral.psychotherapistofficerest.dto.request.PatientRequestDTO;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDTO;
import pl.goral.psychotherapistofficerest.mapper.PatientMapper;
import pl.goral.psychotherapistofficerest.model.Patient;
import pl.goral.psychotherapistofficerest.service.PatientService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<PatientResponseDTO> getPatients(){
        return patientService.findAllPatients();
    }

    @GetMapping("/{id}")
    public Optional<PatientResponseDTO> getPatientById(@PathVariable Long id){
        return patientService.findPatientById(id);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'THERAPIST')")

    public ResponseEntity<PatientResponseDTO> cretePatient(@RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO response = patientService.createPatient(patientRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

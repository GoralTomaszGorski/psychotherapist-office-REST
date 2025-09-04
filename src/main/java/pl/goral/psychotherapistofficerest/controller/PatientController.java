package pl.goral.psychotherapistofficerest.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDTO;
import pl.goral.psychotherapistofficerest.mapper.PatientMapper;
import pl.goral.psychotherapistofficerest.model.Patient;
import pl.goral.psychotherapistofficerest.service.PatientService;

import java.util.List;
import java.util.Optional;

@RestController
@NoArgsConstructor
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
}

package pl.goral.psychotherapistofficerest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Patients", description = "Endpoints for managing patients")

public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all patients", description = "Returns a list of all registered patients. Accessible only by ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<PatientResponseDTO> getPatients(){
        return patientService.findAllPatients();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get patient by ID", description = "Returns patient details by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found and returned"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id){
        return patientService.findPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'THERAPIST')")
    @Operation(summary = "Create a new patient", description = "Creates a new patient record. Accessible by ADMIN or THERAPIST.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Patient created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid patient data"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody  @Valid PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO response = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

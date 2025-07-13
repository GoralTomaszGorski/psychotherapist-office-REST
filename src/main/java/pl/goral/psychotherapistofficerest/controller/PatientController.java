package pl.goral.psychotherapistofficerest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.goral.psychotherapistofficerest.model.Patient;
import pl.goral.psychotherapistofficerest.service.PatientService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/patients")
    public List<Patient> getPatients(){
        return patientService.findAllPatients();
    }

    @GetMapping("/patients/{id}")
    public List<Patient> getPatientById(@PathVariable Long id){
        return patientService.findAllPatients();
    }




}

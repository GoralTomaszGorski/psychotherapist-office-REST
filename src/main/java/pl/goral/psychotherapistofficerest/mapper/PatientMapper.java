package pl.goral.psychotherapistofficerest.mapper;

import org.springframework.stereotype.Component;
import pl.goral.psychotherapistofficerest.dto.request.PatientRequestDTO;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDTO;
import pl.goral.psychotherapistofficerest.model.Patient;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequestDTO dto) {
        Patient patient = new Patient();
        patient.setNick(dto.getNick());
        patient.setName(dto.getName());
        patient.setSurname(dto.getSurname());
        patient.setEmail(dto.getEmail());
        patient.setTelephone(dto.getTelephone());
        patient.setYearOfBrith(dto.getYearOfBirth());
        patient.setInformation(dto.getInformation());
        patient.setApproval(dto.isApproval());
        return patient;
    }

    public PatientResponseDTO toResponseDTO(Patient patient) {
        return new PatientResponseDTO(
                patient.getId(),
                patient.getNick(),
                patient.getName(),
                patient.getSurname(),
                patient.getEmail(),
                patient.getTelephone(),
                patient.getYearOfBrith(),
                patient.getJoinDate(),
                patient.getInformation(),
                patient.isApproval()
        );
    }
}
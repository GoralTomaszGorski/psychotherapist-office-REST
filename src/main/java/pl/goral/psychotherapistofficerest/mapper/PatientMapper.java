package pl.goral.psychotherapistofficerest.mapper;

import org.springframework.stereotype.Component;
import pl.goral.psychotherapistofficerest.dto.request.PatientRequestDTO;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDTO;
import pl.goral.psychotherapistofficerest.model.Patient;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequestDTO dto) {
        Patient patient = new Patient();
        patient.setNick(dto.nick());
        patient.setName(dto.name());
        patient.setSurname(dto.surname());
        patient.setEmail(dto.email());
        patient.setTelephone(dto.telephone());
        patient.setYearOfBrith(dto.yearOfBirth());
        patient.setInformation(dto.information());
        patient.setApproval(dto.approval());
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
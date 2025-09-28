package pl.goral.psychotherapistofficerest.mapper;

import org.springframework.stereotype.Component;
import pl.goral.psychotherapistofficerest.dto.request.PatientRequestDto;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDto;
import pl.goral.psychotherapistofficerest.model.Patient;

@Component
public class PatientMapper {

    public Patient toEntity(PatientRequestDto dto) {
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

    public PatientResponseDto toResponseDto(Patient patient) {
        return new PatientResponseDto(
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
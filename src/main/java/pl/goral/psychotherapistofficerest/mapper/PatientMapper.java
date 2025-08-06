package pl.goral.psychotherapistofficerest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.goral.psychotherapistofficerest.dto.request.PatientRequestDTO;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDTO;
import pl.goral.psychotherapistofficerest.model.Patient;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "id", ignore = true) // Ignoruj id, bo jest generowane przez bazę
    @Mapping(target = "joinDate", ignore = true) // Ignoruj joinDate, ustawiane w serwisie
    Patient toEntity(PatientRequestDTO dto);

    PatientResponseDTO toResponseDTO(Patient patient);
}
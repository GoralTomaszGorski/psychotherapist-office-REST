package pl.goral.psychotherapistofficerest.dto.response;

import java.time.ZonedDateTime;

public record PatientResponseDTO(
        Long id,
        String nick,
        String name,
        String surname,
        String email,
        String telephone,
        int yearOfBirth,
        ZonedDateTime joinDate,
        String information,
        boolean approval
) {}
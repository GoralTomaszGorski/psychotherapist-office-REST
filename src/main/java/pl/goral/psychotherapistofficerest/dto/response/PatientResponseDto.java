package pl.goral.psychotherapistofficerest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDto {
    private Long id;
    private String nick;
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private int yearOfBirth;
    private ZonedDateTime joinDate;
    private String information;
    private boolean approval;
}
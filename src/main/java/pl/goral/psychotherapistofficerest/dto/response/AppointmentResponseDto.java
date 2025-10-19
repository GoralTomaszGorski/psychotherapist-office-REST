package pl.goral.psychotherapistofficerest.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDto {
    private Long id;
    private Long patientId;
    private String patientName;
    private String patientSurname;
    private Long therapyId;
    private String therapyKind;
    private Long calenderId;
    private LocalDate date;
    private LocalTime time;
    private String status;
}
package pl.goral.psychotherapistofficerest.dto.request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDto {
    private Long id;
    private Long patientId;
    private Long therapyId;
    private Long calenderSlotId;
    private String status;
}
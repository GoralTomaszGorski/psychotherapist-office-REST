package pl.goral.psychotherapistofficerest.dto;

import lombok.*;
import pl.goral.psychotherapistofficerest.model.SlotStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalenderSlotDto {
    private Long id;
    private String dayOfWeek;
    private LocalDate date;
    private LocalTime time;
    private String status;      // "FREE", "BUSY", "CANCELLED", "ARCHIVED"
    private String recurrence;  // "ONCE", "WEEKLY", "MONTHLY"


}
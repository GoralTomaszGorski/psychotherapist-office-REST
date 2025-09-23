package pl.goral.psychotherapistofficerest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalenderSlotDto {
    private Long id;
    private String dayOf;     // "2025-09-29"
    private String time;      // "14:00"
    private boolean free; // czy termin zajęty
}
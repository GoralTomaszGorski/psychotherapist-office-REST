package pl.goral.psychotherapistofficerest.mapper;

import org.springframework.stereotype.Component;
import pl.goral.psychotherapistofficerest.dto.CalenderSlotDto;
import pl.goral.psychotherapistofficerest.model.CalenderSlot;
import pl.goral.psychotherapistofficerest.model.SlotRecurrence;
import pl.goral.psychotherapistofficerest.model.SlotStatus;

@Component
public class CalenderSlotMapper {
    public CalenderSlotDto toDto(CalenderSlot entity) {
        if (entity == null) return null;
        return CalenderSlotDto.builder()
                .id(entity.getId())
                .dayOfWeek(entity.getDayOfWeek())
                .time(entity.getTime())
                .date(entity.getDate())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .recurrence(entity.getRecurrence() != null ? entity.getRecurrence().name() : null)
                .build();
    }

    public CalenderSlot toEntity(CalenderSlotDto dto) {
        if (dto == null) return null;
        return CalenderSlot.builder()
                .id(dto.getId())
                .dayOfWeek(dto.getDayOfWeek())
                .date(dto.getDate())
                .time(dto.getTime())
                .status(
                        dto.getStatus() == null
                                ? SlotStatus.FREE
                                : SlotStatus.valueOf(dto.getStatus())
                )
                .recurrence(
                        dto.getRecurrence() == null
                                ? SlotRecurrence.ONCE
                                : SlotRecurrence.valueOf(dto.getRecurrence())
                )
                .build();
    }
}

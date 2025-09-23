package pl.goral.psychotherapistofficerest.mapper;

import org.springframework.stereotype.Component;
import pl.goral.psychotherapistofficerest.dto.CalenderSlotDto;
import pl.goral.psychotherapistofficerest.model.CalenderSlot;

@Component
public class CalenderSlotMapper {
    public CalenderSlotDto toDto(CalenderSlot entity) {
        if (entity == null) return null;
        return CalenderSlotDto.builder()
                .id(entity.getId())
                .dayOf(entity.getDayof())
                .time(entity.getTime())
                .free(entity.isFree())
                .build();
    }

    public CalenderSlot toEntity(CalenderSlotDto dto) {
        if (dto == null) return null;
        CalenderSlot calenderSlot = new CalenderSlot();
        calenderSlot.setDayof(dto.getDayOf());
        calenderSlot.setTime(dto.getTime());
        calenderSlot.setFree(dto.isFree());
        return calenderSlot;
    }
}

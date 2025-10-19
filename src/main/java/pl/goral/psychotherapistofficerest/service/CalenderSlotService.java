package pl.goral.psychotherapistofficerest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.goral.psychotherapistofficerest.dto.CalenderSlotDto;
import pl.goral.psychotherapistofficerest.mapper.CalenderSlotMapper;
import pl.goral.psychotherapistofficerest.model.CalenderSlot;
import pl.goral.psychotherapistofficerest.model.SlotRecurrence;
import pl.goral.psychotherapistofficerest.model.SlotStatus;
import pl.goral.psychotherapistofficerest.repository.CalenderSlotRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalenderSlotService {

    private static final Locale POLISH_LOCALE = Locale.forLanguageTag("pl");

    private final CalenderSlotRepository calenderSlotRepository;
    private final CalenderSlotMapper calenderSlotMapper;


    public CalenderSlotDto findSlotById(Long id) {
        CalenderSlot slot = calenderSlotRepository.findCalenderSlotById(id)

                .orElseThrow(() -> new RuntimeException("Calender slot not found id:" + id));
        return calenderSlotMapper.toDto(slot);
    }


    public CalenderSlot getSlotById(Long id){
        return calenderSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CalendarSlot not found id: " + id));
    }

    @Transactional
    public
    CalenderSlotDto setFreeSlotStatus(CalenderSlotDto slotDto) {
        CalenderSlot calenderSlot = calenderSlotMapper.toEntity(slotDto);
        calenderSlot.setStatus(SlotStatus.FREE);
        calenderSlotRepository.save(calenderSlot);
        return calenderSlotMapper.toDto(calenderSlot);
    }

    @Transactional
    public CalenderSlotDto setSlotStatus(Long id, SlotStatus status) {
        CalenderSlot slot = calenderSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        slot.setStatus(status);
        return calenderSlotMapper.toDto(calenderSlotRepository.save(slot));
    }

    public List<CalenderSlotDto> getSlotsByStatus(SlotStatus status) {
        return calenderSlotRepository.findAllByStatusOrderByDateAscTimeAsc(status)
                .stream()
                .map(calenderSlotMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CalenderSlotDto> getAllSlots() {
        return calenderSlotRepository.findAll()
                .stream()
                .map(calenderSlotMapper::toDto)
                .toList();
    }

    public List<CalenderSlotDto> getFreeSlots() {
        return calenderSlotRepository.findAllByStatusOrderByDateAscTimeAsc(SlotStatus.FREE)
                .stream()
                .map(calenderSlotMapper::toDto)
                .toList();
    }

    @Transactional
    public List<CalenderSlotDto> createRecurringSlots(CalenderSlotDto template, int count) {
        List<CalenderSlotDto> slots = new ArrayList<>();
        LocalDate startDate = template.getDate();
        LocalTime time = template.getTime();
        String dayOfWeek = template.getDayOfWeek();
        SlotRecurrence recurrence = SlotRecurrence.valueOf(template.getRecurrence());

        for (int i = 0; i < count; i++) {
            LocalDate slotDate;
            switch (recurrence) {
                case ONCE -> slotDate = startDate;
                case WEEKLY -> slotDate = startDate.plusWeeks(i);
                case MONTHLY -> slotDate = startDate.plusMonths(i);
                default -> throw new IllegalArgumentException("Unknown recurrence: " + recurrence);
            }
            CalenderSlotDto slot = CalenderSlotDto.builder()
                    .dayOfWeek(slotDate.getDayOfWeek().getDisplayName(TextStyle.FULL, POLISH_LOCALE))
                    .date(slotDate)
                    .time(time)
                    .recurrence(recurrence.name())
                    .status(SlotStatus.FREE.name())
                    .build();
            CalenderSlot entity = calenderSlotMapper.toEntity(slot);
            calenderSlotRepository.save(entity);
            slots.add(calenderSlotMapper.toDto(entity));
            if (recurrence == SlotRecurrence.ONCE) break;
        }
        return slots;
    }

    @Transactional
    public void deleteSlot(Long id) {
        if (!calenderSlotRepository.existsById(id)) {
            throw new RuntimeException("Slot not found");
        }
        calenderSlotRepository.deleteById(id);
    }
    @Transactional
    public void deleteAllSlots() {
        calenderSlotRepository.deleteAll();
    }

    public CalenderSlotDto createCalenderSlot(CalenderSlotDto slotDto) {
        CalenderSlot calenderSlot = calenderSlotMapper.toEntity(slotDto);
        calenderSlotRepository.save(calenderSlot);
        return calenderSlotMapper.toDto(calenderSlot);
    }

    @Transactional
    public CalenderSlotDto updateSlot(Long id, CalenderSlotDto slotDto) {
        CalenderSlot slot = calenderSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        slot.setDayOfWeek(slotDto.getDayOfWeek());
        slot.setDate(slotDto.getDate());
        slot.setTime(slotDto.getTime());
        slot.setStatus(SlotStatus.valueOf(slotDto.getStatus()));
        slot.setRecurrence(SlotRecurrence.valueOf(slotDto.getRecurrence()));

        CalenderSlot saved = calenderSlotRepository.save(slot);
        return calenderSlotMapper.toDto(saved);
    }

}
package pl.goral.psychotherapistofficerest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.goral.psychotherapistofficerest.dto.CalenderSlotDto;
import pl.goral.psychotherapistofficerest.mapper.CalenderSlotMapper;
import pl.goral.psychotherapistofficerest.model.CalenderSlot;
import pl.goral.psychotherapistofficerest.repository.CalenderSlotRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalenderSlotService {

    private final CalenderSlotRepository calenderSlotRepository;
    private final CalenderSlotMapper calenderSlotMapper;

    public CalenderSlotDto setSlot(Long id, String dayOf, String time, boolean free) {
        CalenderSlot calenderSlot = calenderSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        calenderSlot.setDayof(dayOf);
        calenderSlot.setTime(time);
        calenderSlot.setFree(free);
        calenderSlotRepository.save(calenderSlot);
        return calenderSlotMapper.toDto(calenderSlot);
    }

    public CalenderSlotDto getSlot(Long id) {
        CalenderSlot calenderSlot = calenderSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        return calenderSlotMapper.toDto(calenderSlot);
    }


    public CalenderSlotDto getFreeSlotById(Long id) {
        CalenderSlot calenderSlot = calenderSlotRepository.findCalenderSlotByIdAndFreeIsTrue(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        return calenderSlotMapper.toDto(calenderSlot);
    }
    public List<CalenderSlotDto> getAllSlots() {
        return calenderSlotRepository.findAll()
                .stream()
                .map(calenderSlotMapper::toDto)
                .toList();
    }

    public List<CalenderSlotDto> getFreeSlots() {
        return calenderSlotRepository.findAllByFreeIsTrueOrderById()
                .stream()
                .map(calenderSlotMapper::toDto)
                .toList();
    }

    public List<CalenderSlotDto> getBusySlots() {
        return calenderSlotRepository.findAllByFreeIsFalseOrderById()
                .stream()
                .map(calenderSlotMapper::toDto)
                .toList();
    }

    public void deleteSlot(Long id) {
        if (!calenderSlotRepository.existsById(id)) {
            throw new RuntimeException("Slot not found");
        }
        calenderSlotRepository.deleteById(id);
    }

    public void deleteAllSlots() {
        calenderSlotRepository.deleteAll();
    }

    public CalenderSlotDto createCalenderSlot(CalenderSlotDto slotDto) {
        CalenderSlot calenderSlot = calenderSlotMapper.toEntity(slotDto);
        calenderSlotRepository.save(calenderSlot);
        return calenderSlotMapper.toDto(calenderSlot);
    }
}
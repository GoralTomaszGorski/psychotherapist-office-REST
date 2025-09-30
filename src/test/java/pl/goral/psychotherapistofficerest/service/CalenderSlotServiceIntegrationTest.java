package pl.goral.psychotherapistofficerest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import pl.goral.psychotherapistofficerest.dto.CalenderSlotDto;
import pl.goral.psychotherapistofficerest.mapper.CalenderSlotMapper;
import pl.goral.psychotherapistofficerest.model.CalenderSlot;
import pl.goral.psychotherapistofficerest.model.SlotStatus;
import pl.goral.psychotherapistofficerest.repository.CalenderSlotRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({CalenderSlotService.class, CalenderSlotMapper.class})
class CalenderSlotServiceIntegrationTest {

    @Autowired
    private CalenderSlotService service;

    @Autowired
    private CalenderSlotRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        // Dodajemy dwa sloty: jeden wolny, drugi zajęty
        repository.save(CalenderSlot.builder()
                .dayOfWeek("Sobota")
                .date(LocalDate.of(2025, 10, 4))
                .time(LocalTime.of(10, 0))
                .status(SlotStatus.FREE)
                .recurrence("ONCE")
                .build());
        repository.save(CalenderSlot.builder()
                .dayOfWeek("Sobota")
                .date(LocalDate.of(2025, 10, 4))
                .time(LocalTime.of(11, 0))
                .status(SlotStatus.BUSY)
                .recurrence("ONCE")
                .build());
    }

    @Test
    void shouldReturnAllSlots() {
        List<CalenderSlotDto> slots = service.getAllSlots();
        assertThat(slots).isNotEmpty();
    }

    @Test
    void shouldReturnFreeSlots() {
        List<CalenderSlotDto> freeSlots = service.getFreeSlots();
        assertThat(freeSlots).hasSize(1);
        assertThat(freeSlots.get(0).getTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(freeSlots.get(0).getStatus()).isEqualTo("FREE");
    }

    @Test
    void shouldReturnBusySlots() {
        List<CalenderSlotDto> busySlots = service.getSlotsByStatus(SlotStatus.BUSY);
        assertThat(busySlots).hasSize(1);
        assertThat(busySlots.get(0).getTime()).isEqualTo(LocalTime.of(11, 0));
        assertThat(busySlots.get(0).getStatus()).isEqualTo("BUSY");
    }

    @Test
    void shouldUpdateSlot() {
        CalenderSlot slot = repository.findAll().get(0);

        CalenderSlotDto updated = service.setSlot(
                slot.getId(),
                "Sobota",
                LocalTime.of(9, 0).toString(),
                SlotStatus.BUSY // w nowym modelu, tutaj powinno być status BUSY, ale zostawiam zgodnie z metodą!
        );

        assertThat(updated.getDayOfWeek()).isEqualTo("Sobota");
        assertThat(updated.getTime()).isEqualTo(LocalTime.of(9, 0));
        assertThat(updated.getStatus()).isEqualTo("BUSY");
    }

    @Test
    void shouldDeleteSlot() {
        CalenderSlot slot = repository.findAll().get(0);
        service.deleteSlot(slot.getId());

        assertThat(repository.existsById(slot.getId())).isFalse();
    }

    @Test
    void shouldDeleteAllSlots() {
        service.deleteAllSlots();
        assertThat(repository.findAll()).isEmpty();
    }
}
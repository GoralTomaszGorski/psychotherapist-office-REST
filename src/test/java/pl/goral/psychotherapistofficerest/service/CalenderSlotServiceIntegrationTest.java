package pl.goral.psychotherapistofficerest.service;

import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import pl.goral.psychotherapistofficerest.dto.CalenderSlotDto;
import pl.goral.psychotherapistofficerest.mapper.CalenderSlotMapper;
import pl.goral.psychotherapistofficerest.model.CalenderSlot;
import pl.goral.psychotherapistofficerest.repository.CalenderSlotRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({CalenderSlotService.class, CalenderSlotMapper.class})
class CalenderSlotServiceIT {

    @Autowired
    private CalenderSlotService service;

    @Autowired
    private CalenderSlotRepository repository;

    @BeforeEach
    void setup() {
        repository.save(new CalenderSlot(null, "Sobota", "10:00", true));
        repository.save(new CalenderSlot(null, "Sobota", "11:00", false));
    }

    @Test
    void shouldReturnAllSlots() {
        List<CalenderSlotDto> slots = service.getAllSlots();
        assertThat(slots).isNotEmpty();
    }

    @Test
    void shouldReturnFreeSlots() {
        List<CalenderSlotDto> freeSlots = service.getFreeSlots();
        assertThat(freeSlots.getLast().getTime()).isEqualTo("11:00");
    }

    @Test
    void shouldReturnBusySlots() {
        List<CalenderSlotDto> busySlots = service.getBusySlots();
        assertThat(busySlots).hasSize(1);
        assertThat(busySlots.get(0).getTime()).isEqualTo("11:00");
    }

    @Test
    void shouldUpdateSlot() {
        CalenderSlot slot = repository.findAll().get(0);

        CalenderSlotDto updated = service.setSlot(slot.getId(), "2025-09-23", "09:00", false);

        assertThat(updated.getDayOf()).isEqualTo("2025-09-23");
        assertThat(updated.getTime()).isEqualTo("09:00");
        assertThat(updated.isFree()).isFalse();
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

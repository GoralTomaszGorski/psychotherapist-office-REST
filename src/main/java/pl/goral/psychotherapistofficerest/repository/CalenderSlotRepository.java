package pl.goral.psychotherapistofficerest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.goral.psychotherapistofficerest.model.CalenderSlot;
import pl.goral.psychotherapistofficerest.model.SlotStatus;

import java.util.List;
import java.util.Optional;

public interface CalenderSlotRepository extends JpaRepository<CalenderSlot, Long> {

    // Pobiera wszystkie sloty dla konkretnej daty
    List<CalenderSlot> findAllByDateOrderByTimeAsc(java.time.LocalDate date);

    // Pobiera wszystkie sloty dostępne (status AVAILABLE) w danym dniu
    List<CalenderSlot> findAllByDateAndStatusOrderByTimeAsc(java.time.LocalDate date, SlotStatus status);

    List<CalenderSlot> findAllByStatusOrderByDateAscTimeAsc(SlotStatus status);

    Optional<CalenderSlot> findCalenderSlotByIdById(Long id);

    Optional<CalenderSlot> getCalenderSlotsById(Long id);
}

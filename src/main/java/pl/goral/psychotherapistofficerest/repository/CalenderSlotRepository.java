package pl.goral.psychotherapistofficerest.repository;
import org.springframework.data.repository.CrudRepository;
import pl.goral.psychotherapistofficerest.model.CalenderSlot;

import java.util.List;
import java.util.Optional;

public interface CalenderSlotRepository extends CrudRepository<CalenderSlot, Long> {

        List<CalenderSlot> findAllByFreeIsTrueOrderById();

        Optional<CalenderSlot> findCalenderSlotByIdAndFreeIsTrue(long id);

        List<CalenderSlot> findAllByFreeIsFalseOrderById();
}

package pl.goral.psychotherapistofficerest.repository;
import org.springframework.data.repository.CrudRepository;
import pl.goral.psychotherapistofficerest.model.Calender;

import java.util.List;
import java.util.Optional;

public interface CalenderRepository extends CrudRepository<Calender, Long> {

    List<Calender> findAllByFreeIsTrueOrderById();

    Optional<Calender> findCalenderByIdAndFreeIsTrue(long id);

    List<Calender> findAllByDayofIsNotNullOrderById();

}

package pl.goral.psychotherapistofficerest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.goral.psychotherapistofficerest.model.Meeting;

import java.util.List;

public interface MeetingRepository extends JpaRepository <Meeting, Long>{

    List<Meeting>findAllByCalenderIsNotNullOrderByCalender();

    List<Meeting> findMeetingByPatientEmail(String email);

    List<Meeting> findMeetingsByCalenderDayofContainsIgnoreCaseOrPatientSurnameContainsIgnoreCaseOrPatientNameContainsIgnoreCaseOrderByCalender
            (String day, String surname, String name);

    void deleteMeetingById(long id);

    Meeting findMeetingsByCalender_Id(long id);

}

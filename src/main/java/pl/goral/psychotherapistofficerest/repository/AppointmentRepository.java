package pl.goral.psychotherapistofficerest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.goral.psychotherapistofficerest.model.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository <Appointment, Long>{

    List<Appointment>findAllByCalenderIsNotNullOrderByCalender();

    List<Appointment> findMeetingByPatientEmail(String email);

    List<Appointment> findMeetingsByCalenderDayofContainsIgnoreCaseOrPatientSurnameContainsIgnoreCaseOrPatientNameContainsIgnoreCaseOrderByCalender
            (String day, String surname, String name);

    void deleteMeetingById(long id);

    Appointment findMeetingsByCalender_Id(long id);

}

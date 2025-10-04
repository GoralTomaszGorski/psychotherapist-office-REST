package pl.goral.psychotherapistofficerest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.goral.psychotherapistofficerest.model.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByCalenderIsNotNullOrderByCalender();
    List<Appointment> findAllByPatient_Id(Long patientId);
    List<Appointment> findByStatus(String status);
}
package pl.goral.psychotherapistofficerest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.goral.psychotherapistofficerest.model.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByCalenderSlotIsNotNullOrderByCalenderSlot();
    List<Appointment> findAllByPatient_Id(Long patientId);
    List<Appointment> findByStatus(String status);
    @Query("""
    SELECT a FROM Appointment a
    JOIN a.patient p
    JOIN a.calenderSlot c
    WHERE
        LOWER(p.nick) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(p.surname) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(p.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(p.telephone) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR STR(p.yearOfBrith) LIKE CONCAT('%', :keyword, '%')
        OR LOWER(c.dayOfWeek) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR STR(c.date) LIKE CONCAT('%', :keyword, '%')
        OR STR(c.time) LIKE CONCAT('%', :keyword, '%')
""")
    List<Appointment> searchAppointmentsByKeyword(@Param("keyword") String keyword);
}
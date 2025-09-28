package pl.goral.psychotherapistofficerest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.goral.psychotherapistofficerest.model.Appointment;
import pl.goral.psychotherapistofficerest.repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VisitReminderScheduler {

    private final AppointmentRepository appointmentRepository;
    private final EmailMessageService emailMessageService;

//    @Scheduled(cron = "0 0 */2 * * *") //
//    public void sendVisitReminders() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime target = now.plusDays(1).withMinute(0).withSecond(0).withNano(0);
//
//        List<Appointment> appointments = appointmentRepository.findByDateTime(target);
//        for (Appointment appointment : appointments) {
//            if (appointment.getPatient().getEmail() != null && !appointment.getPatient().getEmail().isEmpty()) {
//                emailMessageService.sendVisitReminderMail(
//                        appointment.getPatient().getEmail(),
//                        appointment.getDate().toString(),
//                        appointment.getTime().toString()
//                );
//            }
//        }
//    }
}
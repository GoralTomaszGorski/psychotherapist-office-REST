package pl.goral.psychotherapistofficerest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.goral.psychotherapistofficerest.dto.CalenderSlotDto;
import pl.goral.psychotherapistofficerest.dto.response.AppointmentResponseDto;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDto;
import pl.goral.psychotherapistofficerest.mapper.AppointmentDtoMapper;
import pl.goral.psychotherapistofficerest.model.Appointment;
import pl.goral.psychotherapistofficerest.repository.AppointmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final TherapyService therapyService;
    private final CalenderSlotService calenderSlotService;
    private final AppointmentDtoMapper appointmentDtoMapper;

    @Transactional
    public Appointment createAppointment(Long patientId, Long therapyId, Long calenderSlotId, String status) {
        Appointment appointment = Appointment
                .builder()
                .patient(patientService.getPatientById(patientId))
                .therapy(therapyService.getTherapyById(therapyId))
                .calenderSlot(calenderSlotService.getSlotById(calenderSlotId))
                .status(status)
                .build();
        return appointmentRepository.save(appointment);
    }


    public List<AppointmentResponseDto> findAllAppointments() {
        return appointmentRepository.findAllByCalenderSlotIsNotNullOrderByCalenderSlot()
                .stream()
                .map(AppointmentDtoMapper::toDto)
                .toList();
    }

    public List<AppointmentResponseDto> findByPatientId(Long patientId) {
        return appointmentRepository.findAllByPatient_Id(patientId)
                .stream()
                .map(AppointmentDtoMapper::toDto)
                .toList();
    }

    public List<AppointmentResponseDto> findByStatus(String status) {
        return appointmentRepository.findByStatus(status)
                .stream()
                .map(AppointmentDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Appointment with id " + id + " not found")
        );
        appointmentRepository.delete(appointment);
    }
}
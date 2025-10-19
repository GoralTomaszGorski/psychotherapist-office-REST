package pl.goral.psychotherapistofficerest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pl.goral.psychotherapistofficerest.dto.CalenderSlotDto;
import pl.goral.psychotherapistofficerest.dto.request.AppointmentRequestDto;
import pl.goral.psychotherapistofficerest.dto.response.AppointmentResponseDto;
import pl.goral.psychotherapistofficerest.exception.ResourceNotFoundException;
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

    @Transactional
    public Appointment updateAppointment(Long id, AppointmentRequestDto requestDto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Appointment with id " + id + " not found"
                ));

        if (requestDto.getPatientId() != null) {
            appointment.setPatient(patientService.getPatientById(requestDto.getPatientId()));
        }
        if (requestDto.getTherapyId() != null) {
            appointment.setTherapy(therapyService.getTherapyById(requestDto.getTherapyId()));
        }
        if (requestDto.getCalenderSlotId() != null) {
            appointment.setCalenderSlot(calenderSlotService.getSlotById(requestDto.getCalenderSlotId()));
        }
        if (requestDto.getStatus() != null) {
            appointment.setStatus(requestDto.getStatus());
        }
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

    public List<AppointmentResponseDto> searchAppointments(String keyword) {
        return appointmentRepository.searchAppointmentsByKeyword(keyword)
                .stream()
                .map(AppointmentDtoMapper::toDto)
                .toList();
    }

    @Transactional
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Appointment with id " + id + " not found")
        );
        appointmentRepository.delete(appointment);
        appointmentRepository.delete(appointment);
        CalenderSlotDto calenderSlotDto = calenderSlotService.findSlotById(appointment.getCalenderSlot().getId());
        calenderSlotService.setFreeSlotStatus(calenderSlotDto);
    }
}
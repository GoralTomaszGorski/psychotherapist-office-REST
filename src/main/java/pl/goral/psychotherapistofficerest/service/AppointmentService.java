package pl.goral.psychotherapistofficerest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.goral.psychotherapistofficerest.dto.request.AppointmentRequestDto;
import pl.goral.psychotherapistofficerest.dto.response.AppointmentResponseDto;
import pl.goral.psychotherapistofficerest.mapper.AppointmentDtoMapper;
import pl.goral.psychotherapistofficerest.model.Appointment;
import pl.goral.psychotherapistofficerest.model.CalenderSlot;
import pl.goral.psychotherapistofficerest.model.Patient;
import pl.goral.psychotherapistofficerest.model.Therapy;
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
    public AppointmentResponseDto createAppointment(AppointmentRequestDto dto) {
        Patient patient = patientService.getPatientById(dto.getPatientId());
        Therapy therapy = therapyService.getTherapyById(dto.getTherapyId());
        CalenderSlot calenderSlot = calenderSlotService.getSlotById(dto.getCalenderSlotId());

        Appointment appointment = appointmentDtoMapper.toEntity(dto.getPatientId());
        appointmentRepository.save(appointment);
        return AppointmentDtoMapper.toDto(appointment);
    }


    public List<AppointmentResponseDto> findAllAppointments() {
        return appointmentRepository.findAllByCalenderIsNotNullOrderByCalender()
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
        appointmentRepository.deleteById(id);
    }
}
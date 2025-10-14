package pl.goral.psychotherapistofficerest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.goral.psychotherapistofficerest.dto.CalenderSlotDto;
import pl.goral.psychotherapistofficerest.dto.TherapyDto;
import pl.goral.psychotherapistofficerest.dto.request.PatientRequestDto;
import pl.goral.psychotherapistofficerest.dto.response.AppointmentResponseDto;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDto;
import pl.goral.psychotherapistofficerest.model.Appointment;
import pl.goral.psychotherapistofficerest.repository.AppointmentRepository;
import pl.goral.psychotherapistofficerest.repository.CalenderSlotRepository;
import pl.goral.psychotherapistofficerest.repository.PatientRepository;
import pl.goral.psychotherapistofficerest.repository.TherapyRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
class AppointmentServiceIntegrationTest {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private TherapyService therapyService;
    @Autowired
    private CalenderSlotService calenderSlotService;

    @Autowired
    private CalenderSlotRepository calenderSlotRepository;
    @Autowired
    private TherapyRepository therapyRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    private PatientResponseDto testPatient;
    private TherapyDto testTherapy;
    private CalenderSlotDto testSlot;

    @BeforeEach
    void setup() {
        deleteAll();
        testPatient = patientService.createPatient(createTestPatientRequest());
        testTherapy = therapyService.addTherapy(createTestTherapyDto());
        testSlot = calenderSlotService.createCalenderSlot(createTestCalenderSlotDto());
    }

    private void deleteAll() {
        appointmentRepository.deleteAll();
        calenderSlotRepository.deleteAll();
        therapyRepository.deleteAll();
        patientRepository.deleteAll();

    }

    private CalenderSlotDto createTestCalenderSlotDto() {
        return CalenderSlotDto.builder()
                .dayOfWeek("Sobota")
                .date(LocalDate.of(2025, 10, 4))
                .time(LocalTime.of(10, 0))
                .status("FREE")
                .build();
    }

    private TherapyDto createTestTherapyDto() {
        return TherapyDto.builder()
                .kindOfTherapy("Psychoterapia")
                .description("Opis terapii")
                .price(new BigDecimal("150.00"))
                .build();
    }

    private PatientRequestDto createTestPatientRequest() {
        return PatientRequestDto.builder()
                .nick(null)
                .name("Jan")
                .surname("Kowalski")
                .email("jan@kowalski.pl")
                .telephone("123456789")
                .yearOfBirth(1990)
                .information("info")
                .approval(true)
                .build();
    }


    @Test
    void shouldCreateAppointment() {
        Appointment appointment = appointmentService.createAppointment(testPatient.getId(), testTherapy.getId(), testSlot.getId(),
                "SCHEDULED");
        assertThat(appointment.getId()).isNotNull();
        assertThat(appointment.getStatus()).isEqualTo("SCHEDULED");
    }

    @Test
    void shouldFindAllAppointments() {
        Appointment appointment = appointmentService.createAppointment(testPatient.getId(), testTherapy.getId(), testSlot.getId(),
                "SCHEDULED");
        List<AppointmentResponseDto> all = appointmentService.findAllAppointments();
        assertThat(all).isNotEmpty();
        assertThat(all.get(0).getPatientId()).isEqualTo(testPatient.getId());
    }

    @Test
    void shouldFindByPatientId() {
        Appointment appointment = appointmentService.createAppointment(testPatient.getId(), testTherapy.getId(), testSlot.getId(),
                "SCHEDULED");
        List<AppointmentResponseDto> found = appointmentService.findByPatientId(testPatient.getId());
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getPatientId()).isEqualTo(testPatient.getId());
    }

    @Test
    void shouldFindByStatus() {
        appointmentService.createAppointment(testPatient.getId(), testTherapy.getId(), testSlot.getId(), "FINISHED");
        List<AppointmentResponseDto> found = appointmentService.findByStatus("FINISHED");
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getStatus()).isEqualTo("FINISHED");
    }

    @Test
    void shouldDeleteAppointment() {
        Appointment appointment = appointmentService.createAppointment(testPatient.getId(), testTherapy.getId(), testSlot.getId(), "CANCELLED");
        Long id = appointment.getId();
        appointmentService.deleteAppointment(id);

        List<AppointmentResponseDto> all = appointmentService.findAllAppointments();
        assertThat(all).noneMatch(a -> a.getId().equals(id));
    }
}
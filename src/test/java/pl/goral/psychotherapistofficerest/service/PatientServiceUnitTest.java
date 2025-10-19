package pl.goral.psychotherapistofficerest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.goral.psychotherapistofficerest.dto.request.PatientRequestDto;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDto;
import pl.goral.psychotherapistofficerest.mapper.PatientMapper;
import pl.goral.psychotherapistofficerest.model.Patient;
import pl.goral.psychotherapistofficerest.repository.PatientRepository;
import pl.goral.psychotherapistofficerest.utils.DateInWarsaw;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PatientServiceUnitTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private DateInWarsaw dateInWarsaw;
    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllPatientsAsDTO() {
        // given
        Patient patient = new Patient();
        PatientResponseDto dto = mock(PatientResponseDto.class);

        when(patientRepository.findAll()).thenReturn(List.of(patient));
        when(patientMapper.toResponseDto(patient)).thenReturn(dto);

        // when
        List<PatientResponseDto> result = patientService.findAllPatients();

        // then
        assertThat(result).containsExactly(dto);
        verify(patientRepository).findAll();
        verify(patientMapper).toResponseDto(patient);
    }

    @Test
    void shouldReturnEmptyListWhenNoPatients() {
        when(patientRepository.findAll()).thenReturn(List.of());

        List<PatientResponseDto> result = patientService.findAllPatients();

        assertThat(result).isEmpty();
        verify(patientRepository).findAll();
        verifyNoInteractions(patientMapper);
    }


    PatientRequestDto request = PatientRequestDto.builder()
            .nick(null)  // or "janek" for the second test
            .name("Jan")
            .surname("Kowalski")
            .telephone("123456789")
            .email("jan@example.com")
            .yearOfBirth(1990)
            .information("Some info")
            .approval(true)
            .build();


    @Test
    void shouldCreatePatientAndGenerateNickIfBlank() {
        // given
        PatientRequestDto request = mock(PatientRequestDto.class);
        when(request.getNick()).thenReturn(null);
        when(request.getName()).thenReturn("Jan");
        when(request.getSurname()).thenReturn("Kowalski");
        when(request.getEmail()).thenReturn("ecample@email.com");
        when(request.getYearOfBirth()).thenReturn(1988);
        when(request.getTelephone()).thenReturn("123456789");

        Patient patient = new Patient();
        when(patientMapper.toEntity(request)).thenReturn(patient);



        Patient savedPatient = new Patient();
        when(patientRepository.save(patient)).thenReturn(savedPatient);

        PatientResponseDto dto = mock(PatientResponseDto.class);
        when(patientMapper.toResponseDto(savedPatient)).thenReturn(dto);

        // when
        PatientResponseDto result = patientService.createPatient(request);

        // then
        assertThat(patient.getNick()).isEqualTo("789JKo");
        assertThat(result).isEqualTo(dto);

        verify(patientRepository).save(patient);
        verify(patientMapper).toEntity(request);
        verify(patientMapper).toResponseDto(savedPatient);
        verify(dateInWarsaw).getLocalDateInWarsaw();
    }

    @Test
    void shouldCreatePatientWithProvidedNick() {
        PatientRequestDto request = mock(PatientRequestDto.class);
        when(request.getNick()).thenReturn("janek");
        when(request.getName()).thenReturn("Jan");
        when(request.getSurname()).thenReturn("Kowalski");
        when(request.getEmail()).thenReturn("ecample@email.com");
        when(request.getYearOfBirth()).thenReturn(1988);
        when(request.getTelephone()).thenReturn("123456789");

        Patient patient = new Patient();
        when(patientMapper.toEntity(request)).thenReturn(patient);

        Patient savedPatient = new Patient();
        savedPatient.setNick("janek");
        when(patientRepository.save(patient)).thenReturn(savedPatient);

        PatientResponseDto dto = mock(PatientResponseDto.class);
        when(patientMapper.toResponseDto(savedPatient)).thenReturn(dto);

        PatientResponseDto result = patientService.createPatient(request);

        assertThat(patient.getNick()).isEqualTo("janek");
        assertThat(result).isEqualTo(dto);
    }
}











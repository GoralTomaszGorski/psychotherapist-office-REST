package pl.goral.psychotherapistofficerest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.goral.psychotherapistofficerest.dto.request.PatientRequestDTO;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDTO;
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
        PatientResponseDTO dto = mock(PatientResponseDTO.class);

        when(patientRepository.findAll()).thenReturn(List.of(patient));
        when(patientMapper.toResponseDTO(patient)).thenReturn(dto);

        // when
        List<PatientResponseDTO> result = patientService.findAllPatients();

        // then
        assertThat(result).containsExactly(dto);
        verify(patientRepository).findAll();
        verify(patientMapper).toResponseDTO(patient);
    }

    @Test
    void shouldReturnEmptyListWhenNoPatients() {
        when(patientRepository.findAll()).thenReturn(List.of());

        List<PatientResponseDTO> result = patientService.findAllPatients();

        assertThat(result).isEmpty();
        verify(patientRepository).findAll();
        verifyNoInteractions(patientMapper);
    }

    @Test
    void shouldReturnPatientById() {
        Patient patient = new Patient();
        PatientResponseDTO dto = mock(PatientResponseDTO.class);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientMapper.toResponseDTO(patient)).thenReturn(dto);

        Optional<PatientResponseDTO> result = patientService.findPatientById(1L);

        assertThat(result).hasValue(dto);
        verify(patientRepository).findById(1L);
        verify(patientMapper).toResponseDTO(patient);
    }

    @Test
    void shouldReturnEmptyWhenPatientNotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<PatientResponseDTO> result = patientService.findPatientById(1L);

        assertThat(result).isEmpty();
        verify(patientRepository).findById(1L);
        verifyNoInteractions(patientMapper);
    }

    PatientRequestDTO request = PatientRequestDTO.builder()
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
        PatientRequestDTO request = mock(PatientRequestDTO.class);
        when(request.getNick()).thenReturn(null);
        when(request.getName()).thenReturn("Jan");
        when(request.getSurname()).thenReturn("Kowalski");
        when(request.getTelephone()).thenReturn("123456789");

        Patient patient = new Patient();
        when(patientMapper.toEntity(request)).thenReturn(patient);



        Patient savedPatient = new Patient();
        when(patientRepository.save(patient)).thenReturn(savedPatient);

        PatientResponseDTO dto = mock(PatientResponseDTO.class);
        when(patientMapper.toResponseDTO(savedPatient)).thenReturn(dto);

        // when
        PatientResponseDTO result = patientService.createPatient(request);

        // then
        assertThat(patient.getNick()).isEqualTo("789JKo");
        assertThat(result).isEqualTo(dto);

        verify(patientRepository).save(patient);
        verify(patientMapper).toEntity(request);
        verify(patientMapper).toResponseDTO(savedPatient);
        verify(dateInWarsaw).getLocalDateInWarsaw();
    }

    @Test
    void shouldCreatePatientWithProvidedNick() {
        PatientRequestDTO request = mock(PatientRequestDTO.class);
        when(request.getNick()).thenReturn("janek");
        when(request.getName()).thenReturn("Jan");
        when(request.getSurname()).thenReturn("Kowalski");
        when(request.getTelephone()).thenReturn("123456789");

        Patient patient = new Patient();
        when(patientMapper.toEntity(request)).thenReturn(patient);

        Patient savedPatient = new Patient();
        savedPatient.setNick("janek");
        when(patientRepository.save(patient)).thenReturn(savedPatient);

        PatientResponseDTO dto = mock(PatientResponseDTO.class);
        when(patientMapper.toResponseDTO(savedPatient)).thenReturn(dto);

        PatientResponseDTO result = patientService.createPatient(request);

        assertThat(patient.getNick()).isEqualTo("janek");
        assertThat(result).isEqualTo(dto);
    }
}











package pl.goral.psychotherapistofficerest.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.goral.psychotherapistofficerest.dto.request.PatientRequestDTO;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class PatientServiceIntegrationTest {

    @Autowired
    private PatientService patientService;

    @Test
    void shouldCreateAndFindPatient() {
        // given
        PatientRequestDTO request = new PatientRequestDTO(
                "janek", "Jan","Kowalski" , "jan@kowalski.pl", "123456789", 2025, "some info", true
        );

        // when
        PatientResponseDTO created = patientService.createPatient(request);

        // then
        assertThat(created.getName()).isEqualTo("Jan");
        assertThat(created.getNick()).isEqualTo("janek");
        assertThat(created.getEmail()).isEqualTo("jan@kowalski.pl");

        List<PatientResponseDTO> all = patientService.findAllPatients();
        assertThat(all).extracting(PatientResponseDTO::getId).contains(created.getId());

        Optional<PatientResponseDTO> byId = patientService.findPatientById(created.getId());
    }

    @Test
    void shouldGenerateNickWhenBlank() {
        PatientRequestDTO request = new PatientRequestDTO(
                "", "Ania", "Nowak", "Aan@et.pl", "4546546123", 2025, "some info", true
        );
        PatientResponseDTO created = patientService.createPatient(request);

        // Nick powinien być wygenerowany: ostatnie 3 cyfry + pierwsza litera imienia + 2 pierwsze litery nazwiska
        assertThat(created.getNick()).isEqualTo("123ANo");
    }

    @Test
    @Disabled
    void shouldReturnEmptyForNotExistingPatient() {
        Optional<PatientResponseDTO> notFound = patientService.findPatientById(99999L);
        assertThat(notFound).isEmpty();
    }


    @Test
    void shouldNotAllowInvalidEmail() {
        PatientRequestDTO request = new PatientRequestDTO(
                "nick", "Tom", "Smith", "not-an-email", "123456789", 1990, "info", true
        );
        assertThatThrownBy(() -> patientService.createPatient(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("email");
    }

    @Test
    void shouldNotAllowPatientOlderThan90Years() {
        int yearOfBirth = LocalDate.now().getYear() - 91;
        PatientRequestDTO request = new PatientRequestDTO(
                "nick", "Tom", "Smith", "tom@smith.com", "123456789", yearOfBirth, "info", true
        );
        assertThatThrownBy(() -> patientService.createPatient(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("age");
    }

    @Test
    void shouldNotAllowMissingNameOrSurname() {
        // Missing name
        PatientRequestDTO request1 = new PatientRequestDTO(
                "nick", "", "Smith", "tom@smith.com", "123456789", 1990, "info", true
        );
        assertThatThrownBy(() -> patientService.createPatient(request1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");

        // Missing surname
        PatientRequestDTO request2 = new PatientRequestDTO(
                "nick", "Tom", "", "tom@smith.com", "123456789", 1990, "info", true
        );
        assertThatThrownBy(() -> patientService.createPatient(request2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("surname");
    }
}
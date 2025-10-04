package pl.goral.psychotherapistofficerest.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.goral.psychotherapistofficerest.dto.request.PatientRequestDto;
import pl.goral.psychotherapistofficerest.dto.response.PatientResponseDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("dev")
class PatientServiceIntegrationTest {

    @Autowired
    private PatientService patientService;

    @Test
    void shouldCreateAndFindPatient() {
        // given
        PatientRequestDto request = new PatientRequestDto(
                "janek", "Jan","Kowalski" , "jan@kowalski.pl", "123456789", 2000, "some info", true
        );

        // when
        PatientResponseDto created = patientService.createPatient(request);

        // then
        assertThat(created.getName()).isEqualTo("Jan");
        assertThat(created.getNick()).isEqualTo("janek");
        assertThat(created.getEmail()).isEqualTo("jan@kowalski.pl");

        List<PatientResponseDto> all = patientService.findAllPatients();
        assertThat(all).extracting(PatientResponseDto::getId).contains(created.getId());
    }

    @Test
    void shouldGenerateNickWhenBlank() {
        PatientRequestDto request = new PatientRequestDto(
                "", "Ania", "Nowak", "Aan@et.pl", "4546546123", 1990, "some info", true
        );
        PatientResponseDto created = patientService.createPatient(request);

        // Nick powinien być wygenerowany: ostatnie 3 cyfry + pierwsza litera imienia + 2 pierwsze litery nazwiska
        assertThat(created.getNick()).isEqualTo("123ANo");
    }

    @Test
    void shouldReturnEmptyForNotExistingPatient() {
        Optional<PatientResponseDto> notFound = Optional.ofNullable(patientService.findPatientById(99999L));
        assertThat(notFound).isEmpty();
    }


    @Test
    void shouldNotAllowInvalidEmail() {
        PatientRequestDto request = new PatientRequestDto(
                "nick", "Tom", "Smith", "not-an-email", "123456789", 1990, "info", true
        );
        assertThatThrownBy(() -> patientService.createPatient(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("email");
    }

    @Test
    void shouldNotAllowPatientOlderThan90Years() {
        int yearOfBirth = LocalDate.now().getYear() - 91;
        PatientRequestDto request = new PatientRequestDto(
                "nick", "Tom", "Smith", "tom@smith.com", "123456789", yearOfBirth, "info", true
        );
        assertThatThrownBy(() -> patientService.createPatient(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Patient is too old");
    }

    @Test
    void shouldNotAllowMissingNameOrSurname() {
        // Missing name
        PatientRequestDto request1 = new PatientRequestDto(
                "nick", "", "Smith", "tom@smith.com", "123456789", 1990, "info", true
        );
        assertThatThrownBy(() -> patientService.createPatient(request1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("name");

        // Missing surname
        PatientRequestDto request2 = new PatientRequestDto(
                "nick", "Tom", "", "tom@smith.com", "123456789", 1990, "info", true
        );
        assertThatThrownBy(() -> patientService.createPatient(request2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("surname");
    }
}
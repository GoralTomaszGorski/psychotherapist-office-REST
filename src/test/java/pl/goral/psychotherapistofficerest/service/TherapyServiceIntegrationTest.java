package pl.goral.psychotherapistofficerest.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.goral.psychotherapistofficerest.dto.TherapyDto;
import pl.goral.psychotherapistofficerest.repository.TherapyRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TherapyServiceIntegrationTest {

    @Autowired
    private TherapyService therapyService;

    @Autowired
    private TherapyRepository therapyRepository;

    @Test
    void shouldAddAndGetTherapyViaDto() {
        // given
        TherapyDto toSave = TherapyDto.builder()
                .kindOfTherapy("Test therapy")
                .description("Opis testowy")
                .price(BigDecimal.valueOf(200))
                .build();

        // when
        TherapyDto saved = therapyService.addTherapy(toSave);

        // then
        assertThat(saved.getId()).isNotNull();
        TherapyDto found = therapyService.getTherapyById(saved.getId());
        assertThat(found.getKindOfTherapy()).isEqualTo("Test therapy");
        assertThat(found.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(200));

    }

    @Test
    void shouldUpdateTherapyViaDto() {
        // given: najpierw dodaj terapię
        TherapyDto created = therapyService.addTherapy(
                TherapyDto.builder()
                        .kindOfTherapy("Old therapy")
                        .description("Desc")
                        .price(BigDecimal.valueOf(100))
                        .build()
        );

        // when: aktualizujemy pola
        TherapyDto updateDto = TherapyDto.builder()
                .kindOfTherapy("New therapy")
                .description("Nowy opis")
                .price(BigDecimal.valueOf(300))
                .build();

        TherapyDto updated = therapyService.updateTherapy(created.getKindOfTherapy(), updateDto);

        // then
        assertThat(updated.getKindOfTherapy()).isEqualTo("New therapy");
        assertThat(updated.getDescription()).isEqualTo("Nowy opis");
        assertThat(updated.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(300));
    }

    @Test
    @Disabled
    void shouldReturnAllTherapies() {
        therapyRepository.deleteAll();

        therapyService.addTherapy(TherapyDto.builder()
                .kindOfTherapy("A")
                .description("A desc")
                .price(BigDecimal.TEN)
                .build());
        therapyService.addTherapy(TherapyDto.builder()
                .kindOfTherapy("B")
                .description("B desc")
                .price(BigDecimal.ONE)
                .build());

        List<TherapyDto> all = therapyService.getAllTherapies();
        assertThat(all).hasSizeGreaterThanOrEqualTo(2);
        assertThat(all).extracting(TherapyDto::getKindOfTherapy).contains("A", "B");
    }
}
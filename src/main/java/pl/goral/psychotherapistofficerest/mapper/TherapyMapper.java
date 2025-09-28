package pl.goral.psychotherapistofficerest.mapper;

import org.springframework.stereotype.Component;
import pl.goral.psychotherapistofficerest.dto.TherapyDto;
import pl.goral.psychotherapistofficerest.model.Therapy;

@Component
public class TherapyMapper {

    public TherapyDto toDto(Therapy entity) {
        if (entity == null) return null;
        return TherapyDto.builder()
                .id(entity.getId())
                .kindOfTherapy(entity.getKindOfTherapy())
                .description(entity.getDescription())
                .price(entity.getPrice())
                    .build();
    }

    public Therapy toEntity(TherapyDto dto) {
        if (dto == null) return null;
        Therapy therapy = new Therapy();
        therapy.setKindOfTherapy(dto.getKindOfTherapy());
        therapy.setDescription(dto.getDescription());
        therapy.setPrice(dto.getPrice());
        return therapy;
    }
}
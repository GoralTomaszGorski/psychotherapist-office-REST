package pl.goral.psychotherapistofficerest.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TherapyDto {
    private Long id;
    private String kindOfTherapy;
    private String description;
    private BigDecimal price;
}


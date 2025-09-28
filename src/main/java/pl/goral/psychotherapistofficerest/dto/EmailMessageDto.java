package pl.goral.psychotherapistofficerest.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessageDto {
    private String recipient;
    private String subject;
    private String body;
    private String type;
}
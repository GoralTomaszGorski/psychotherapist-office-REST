package pl.goral.psychotherapistofficerest.mapper;

import org.springframework.stereotype.Component;
import pl.goral.psychotherapistofficerest.dto.EmailMessageDto;
import pl.goral.psychotherapistofficerest.model.EmailMessage;

@Component
public class EmailMessageMapper {
    public EmailMessageDto toDto(EmailMessage entity) {
        return EmailMessageDto.builder()
                .recipient(entity.getRecipient())
                .subject(entity.getSubject())
                .body(entity.getBody())
                .type(entity.getType())
                .build();
    }

    public EmailMessage toEntity(EmailMessageDto dto) {
        return EmailMessage.builder()
                .recipient(dto.getRecipient())
                .subject(dto.getSubject())
                .body(dto.getBody())
                .type(dto.getType())
                .build();
    }
}
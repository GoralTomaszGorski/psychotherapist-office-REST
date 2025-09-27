package pl.goral.psychotherapistofficerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.goral.psychotherapistofficerest.model.EmailMessage;

public interface EmailMessageRepository extends JpaRepository<EmailMessage, Long> {
}
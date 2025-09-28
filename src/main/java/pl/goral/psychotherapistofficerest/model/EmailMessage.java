package pl.goral.psychotherapistofficerest.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipient;
    private String subject;
    private String body;
    private String status; // np. success/error
    private String type;   // np. RESET_PASSWORD, VISIT, MESSAGE_BOX
}
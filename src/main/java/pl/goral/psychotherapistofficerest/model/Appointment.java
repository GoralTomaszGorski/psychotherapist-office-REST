package pl.goral.psychotherapistofficerest.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "appointment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "therapy_id", referencedColumnName = "id")
    private Therapy therapy;
    @OneToOne
    @JoinColumn(name = "calender_id", referencedColumnName = "id")
    private CalenderSlot calenderSlot;

    private String status; // e.g. SCHEDULED, CANCELLED, COMPLETED
}
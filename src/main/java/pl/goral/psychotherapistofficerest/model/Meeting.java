package pl.goral.psychotherapistofficerest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
    @Table(name = "meeting")

    public class Meeting {
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
        private Calender calender;
}

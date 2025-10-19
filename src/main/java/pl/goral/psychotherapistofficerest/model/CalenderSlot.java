package pl.goral.psychotherapistofficerest.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "calender")
public class CalenderSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_week")
    private String dayOfWeek; // np. "poniedziałek"

    @Column(name = "date")
    private LocalDate date;   // np. 2025-09-29

    @Column(name = "time")
    private LocalTime time;      // np. "14.00"

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SlotStatus status; // FREE, BUSY, CANCELLED, ARCHIVED

    @Enumerated(EnumType.STRING)
    @Column(name = "recurrence")
    private SlotRecurrence recurrence; // ONCE, WEEKLY, MONTHLY
}
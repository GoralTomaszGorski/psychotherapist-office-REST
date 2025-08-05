package pl.goral.psychotherapistofficerest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "counter")
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "refresh", nullable = false)
    private int refresh;

    @Column(name = "entry", nullable = false)
    private int entry;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "url", nullable = false)
    private String url;
}

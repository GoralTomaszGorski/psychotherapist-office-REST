package pl.goral.psychotherapistofficerest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "calender")

public class Calender {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "dayof")
    private String dayof;
    private String time;
    private boolean free;

}

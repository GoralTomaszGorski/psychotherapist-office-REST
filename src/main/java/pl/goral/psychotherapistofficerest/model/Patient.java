package pl.goral.psychotherapistofficerest.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String nick;
    private String name;
    private String surname;
    private String email;
    private String telephone;
    @Column(name = "YEAR_OF_BRITH")
    private int yearOfBrith;
    @Column(name = "JOIN_DATE")
    private ZonedDateTime joinDate;
    private String  information;
    private boolean  approval;

}

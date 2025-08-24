package pl.goral.psychotherapistofficerest.model;
import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "YEAR_OF_BIRTH")
    private int yearOfBrith;
    @Column(name = "JOIN_DATE")
    private ZonedDateTime joinDate;
    private String  information;
    private boolean  approval;

}

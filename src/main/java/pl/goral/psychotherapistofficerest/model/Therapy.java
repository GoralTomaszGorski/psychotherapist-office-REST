package pl.goral.psychotherapistofficerest.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Therapy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "KIND_OF_THERAPY")
    private String kindOfTherapy;
    private String description;
    private BigDecimal price;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKindOfTherapy() {
        return kindOfTherapy;
    }

    public void setKindOfTherapy(String kindOfTherapy) {
        this.kindOfTherapy = kindOfTherapy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

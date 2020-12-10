package mk.finki.ukim.mk.lab.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mk.finki.ukim.mk.lab.model.enumerations.BalloonType;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Balloon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;

    String description;

    @ManyToOne
    Manufacturer manufacturer;

    //    @Enumerated(EnumType.STRING)
    BalloonType type;


    public Balloon(String name, String description, Manufacturer manufacturer, BalloonType type) {
        this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.type = type;
    }


}

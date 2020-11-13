package mk.finki.ukim.mk.lab.model;

import lombok.Data;
import mk.finki.ukim.mk.lab.model.enumerations.BalloonType;

@Data
public class Balloon {
    Long id;
    String name;
    String description;
    Manufacturer manufacturer;
    BalloonType type;


    public Balloon(String name, String description, Manufacturer manufacturer, BalloonType type) {
        this.id = (long) (Math.random() * 1000);
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.type = type;
    }


}

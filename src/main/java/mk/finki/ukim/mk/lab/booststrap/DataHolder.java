package mk.finki.ukim.mk.lab.booststrap;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.enumerations.BalloonType;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Balloon> balloonList = new ArrayList<>();
    public static List<Manufacturer> manufacturerList = new ArrayList<>();
    public static List<Order> ordersList = new ArrayList<>();

    @PostConstruct
    public void init() {

        manufacturerList.add(new Manufacturer("Nike", "USA", "Broadway NY 1"));
        manufacturerList.add(new Manufacturer("Adidas", "USA", "Broadway NY 2"));
        manufacturerList.add(new Manufacturer("Puma", "USA", "Broadway NY 3"));
        manufacturerList.add(new Manufacturer("Apple", "USA", "Broadway NY 4"));
        manufacturerList.add(new Manufacturer("Opel", "USA", "Broadway NY 5"));

        for (int i = 1; i <= 10; i++) {
            BalloonType type = BalloonType.OVAL;
            if (i % 2 == 0) {
                type = BalloonType.HEART;
            }
            balloonList.add(new Balloon(
                    String.format("Balloon %d", i),
                    String.format("Description for balloon %d", i),
                    manufacturerList.get(1),
                    type
            ));
        }
    }


}

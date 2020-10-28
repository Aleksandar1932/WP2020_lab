package mk.finki.ukim.mk.lab.booststrap;

import mk.finki.ukim.mk.lab.model.Balloon;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Balloon> balloonList = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 10; i++) {
            balloonList.add(new Balloon(
                    String.format("Balloon %d", i),
                    String.format("Description for balloon %d", i)));
        }
    }


}

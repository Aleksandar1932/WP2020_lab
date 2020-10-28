package mk.finki.ukim.mk.lab.repository;

import mk.finki.ukim.mk.lab.booststrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Balloon;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BalloonRepository {
    public List<Balloon> findAllBalloons() {
        return DataHolder.balloonList;
    }

    public List<Balloon> findAllByNameOrDescription(String text) {
        return DataHolder.balloonList.stream().filter(b -> b.getName().equals(text) || b.getDescription().equals(text)).collect(Collectors.toList());
    }
}

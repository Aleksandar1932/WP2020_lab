package mk.finki.ukim.mk.lab.repository.impl;

import mk.finki.ukim.mk.lab.booststrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.enumerations.BalloonType;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryBalloonRepository {
    public List<Balloon> findAllBalloons() {
        return DataHolder.balloonList;
    }

    public List<Balloon> findAllByNameOrDescription(String text) {
        return DataHolder.balloonList.stream().filter(b -> b.getName().contains(text) || b.getDescription().contains(text)).collect(Collectors.toList());
    }

    public Optional<Balloon> save(String name, String description, Manufacturer manufacturer, BalloonType type) {
        Balloon balloon = new Balloon(name, description, manufacturer, type);

        DataHolder.balloonList.removeIf(balloon1 -> balloon1.getName().equals(name));

        DataHolder.balloonList.add(balloon);

        return Optional.of(balloon);
    }

    public Boolean deleteById(Long id) {
        return DataHolder.balloonList.removeIf(balloon -> balloon.getId().equals(id));
    }

    public Optional<Balloon> findById(Long id) {
        return DataHolder.balloonList.stream().filter(balloon -> balloon.getId().equals(id)).findFirst();
    }
}

package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.enumerations.BalloonType;

import java.util.List;
import java.util.Optional;

public interface BalloonService {
    List<Balloon> listAll();

    List<Balloon> listAllSortedByType(BalloonType type);

    List<Balloon> searchByNameOrDescription(String text);

    Optional<Balloon> save(String name, String description, Long manufacturerId, BalloonType type);

    void deleteById(Long id);

    Optional<Balloon> findById(Long id);
}

package mk.finki.ukim.mk.lab.repository.jpa;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.enumerations.BalloonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalloonRepository extends JpaRepository<Balloon, Long> {
    List<Balloon> findAllByNameOrDescription(String name, String description);

    List<Balloon> findAllByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContainingOrManufacturerNameIgnoreCaseContaining(String name, String description, String manufacturer_name);

    Optional<Balloon> findBalloonByName(String name);
}

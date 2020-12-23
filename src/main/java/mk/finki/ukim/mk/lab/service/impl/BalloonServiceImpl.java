package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.enumerations.BalloonType;
import mk.finki.ukim.mk.lab.model.exceptions.*;
import mk.finki.ukim.mk.lab.repository.jpa.BalloonRepository;
import mk.finki.ukim.mk.lab.repository.jpa.ManufacturerRepository;
import mk.finki.ukim.mk.lab.service.BalloonService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BalloonServiceImpl implements BalloonService {

    // Constructor-based dependency injection for the BalloonRepository
    private final BalloonRepository balloonRepository;
    private final ManufacturerRepository manufacturerRepository;

    public BalloonServiceImpl(BalloonRepository balloonRepository, ManufacturerRepository manufacturerRepository) {
        this.balloonRepository = balloonRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Balloon> listAll() {
        return balloonRepository.findAll();
    }

    @Override
    public List<Balloon> filterByType(BalloonType type) {
        return balloonRepository.findAll().stream().filter(balloon -> balloon.getType().equals(type)).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public Optional<Balloon> save(String name, String description, Long manufacturerId, BalloonType type, Long balloonToUpdateId) {
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId).orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));

        Balloon b = new Balloon(name, description, manufacturer, type);

        if (balloonToUpdateId != null) {
            balloonRepository.deleteById(balloonToUpdateId);
        }

        if (name.isEmpty()) {
            throw new InvalidBalloonNameException();
        }
        if (description.isEmpty()) {
            throw new InvalidBalloonDescriptionException();
        }
        if (type == null) {
            throw new InvalidBalloonTypeException();
        }

        return Optional.of(this.balloonRepository.save(b));
    }

    @Override
    public List<Balloon> searchByNameOrDescription(String text) {
        if (text == null || text.isEmpty()) {
            throw new InvalidSearchTextException();
        }

        return balloonRepository.findAllByNameOrDescription(text, text);
    }

    @Override
    public void deleteById(Long id) {
        if (!balloonRepository.existsById(id)) {
            throw new BalloonNotFoundException(id);
        }
        balloonRepository.deleteById(id);

    }

    @Override
    public Optional<Balloon> findById(Long id) {
        return Optional.of(balloonRepository.findById(id).orElseThrow(() -> new BalloonNotFoundException(id)));
    }

    @Override
    public List<Balloon> findAllByText(String text) {

        if (text.isEmpty()) {
            throw new InvalidSearchTextException();
        }
        return balloonRepository.findAllByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContainingOrManufacturerNameIgnoreCaseContaining(text, text, text);
    }
}

package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.enumerations.BalloonType;
import mk.finki.ukim.mk.lab.model.exceptions.ManufacturerNotFoundException;
import mk.finki.ukim.mk.lab.repository.BalloonRepository;
import mk.finki.ukim.mk.lab.repository.InMemoryManufacturerRepository;
import mk.finki.ukim.mk.lab.service.BalloonService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BalloonServiceImpl implements BalloonService {

    // Constructor-based dependency injection for the BalloonRepository
    private final BalloonRepository balloonRepository;
    private final InMemoryManufacturerRepository manufacturerRepository;

    public BalloonServiceImpl(BalloonRepository balloonRepository, InMemoryManufacturerRepository manufacturerRepository) {
        this.balloonRepository = balloonRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Balloon> listAll() {
        return balloonRepository.findAllBalloons();
    }

    @Override
    public List<Balloon> listAllSortedByType(BalloonType type) {
        return  balloonRepository.findAllBalloons().stream().filter(balloon -> balloon.getType().equals(type)).collect(Collectors.toList());
    }

    @Override
    public Optional<Balloon> save(String name, String description, Long manufacturerId, BalloonType type) {

        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId).orElseThrow(() -> new ManufacturerNotFoundException(manufacturerId));

        return this.balloonRepository.save(name, description, manufacturer, type);
    }

    @Override
    public List<Balloon> searchByNameOrDescription(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return balloonRepository.findAllByNameOrDescription(text);
    }

    @Override
    public void deleteById(Long id) {
        balloonRepository.deleteById(id);
    }

    @Override
    public Optional<Balloon> findById(Long id) {
        return balloonRepository.findById(id);
    }
}

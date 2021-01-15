package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidManufacturerAddressException;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidManufacturerCountryException;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidManufacturerNameException;
import mk.finki.ukim.mk.lab.model.exceptions.ManufacturerNotFoundException;
import mk.finki.ukim.mk.lab.repository.jpa.ManufacturerRepository;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> findAll() {
        return this.manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> save(String name, String country, String address, Long manufacturerToUpdateId) {
        if (manufacturerToUpdateId != null) {
            this.manufacturerRepository.deleteById(manufacturerToUpdateId);
        }

        if (name.isEmpty()) {
            throw new InvalidManufacturerNameException();
        }

        if (country.isEmpty()) {
            throw new InvalidManufacturerCountryException();
        }

        if (address.isEmpty()) {
            throw new InvalidManufacturerAddressException();
        }

        Manufacturer manufacturer = new Manufacturer(name, country, address);

        return Optional.of(this.manufacturerRepository.save(manufacturer));
    }

    @Override
    public void deleteById(Long id) {
        if (!manufacturerRepository.existsById(id)) {
            throw new ManufacturerNotFoundException(id);
        }

        manufacturerRepository.deleteById(id);
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return Optional.of(this.manufacturerRepository.findById(id).orElseThrow(() -> new ManufacturerNotFoundException(id)));
    }
}

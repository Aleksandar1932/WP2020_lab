package mk.finki.ukim.mk.lab.repository.impl;

import mk.finki.ukim.mk.lab.booststrap.DataHolder;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryManufacturerRepository {
    public List<Manufacturer> findAll() {
        return DataHolder.manufacturerList;
    }

    public Optional<Manufacturer> findById(Long id) {
        return DataHolder.manufacturerList.stream().filter(manufacturer -> manufacturer.getId().equals(id)).findFirst();
    }
}

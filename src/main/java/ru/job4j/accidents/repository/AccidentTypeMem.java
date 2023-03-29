package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentTypeMem implements AccidentTypeRepository {

    private final Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger();

    public AccidentTypeMem() {
        add(new AccidentType(1, "Две машины"));
        add(new AccidentType(2, "Машина и человек"));
        add(new AccidentType(3, "Машина и велосипед"));
    }

    public AccidentType add(AccidentType accidentType) {
        accidentType.setId(id.incrementAndGet());
        accidentTypes.put(accidentType.getId(), accidentType);
        return accidentType;
    }

    public List<AccidentType> findAll() {
        return new ArrayList<AccidentType>(accidentTypes.values());
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypes.get(id));
    }

    public boolean replace(int id, AccidentType accidentType) {
        return accidentTypes.replace(id, accidentType) != null;
    }

    public boolean delete(int id) {
        return accidentTypes.remove(id) != null;
    }
}

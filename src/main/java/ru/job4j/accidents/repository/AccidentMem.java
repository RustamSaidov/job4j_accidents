package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements AccidentRepository {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger();
    private AccidentTypeRepository accidentTypeRepository = new AccidentTypeMem();

    public AccidentMem() {
        add(new Accident(0, "name1", "text1", "address1", accidentTypeRepository.findById(1).get()));
        add(new Accident(0, "name2", "text2", "address2", accidentTypeRepository.findById(2).get()));
    }

    public Accident add(Accident accident) {
        accident.setId(id.incrementAndGet());
        accidents.put(accident.getId(), accident);
        return accident;
    }

    public List<Accident> findAll() {
        return new ArrayList<Accident>(accidents.values());
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public boolean replace(int id, Accident accident) {
        return accidents.replace(id, accident) != null;
    }

    public boolean delete(int id) {
        return accidents.remove(id) != null;
    }
}

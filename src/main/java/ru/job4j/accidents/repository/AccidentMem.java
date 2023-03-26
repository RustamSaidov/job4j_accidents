package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccidentMem implements AccidentRepository {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private int id = 0;

    public Accident add(Accident accident) {
        accident.setId(id++);
        accidents.put(accident.getId(), accident);
        return accident;
    }

    public List<Accident> findAll() {
        return new ArrayList<Accident>(accidents.values());
    }

    public Optional<Accident> findById(int id) {
        Optional<Accident> result = Optional.empty();
        if (accidents.containsKey(id)) {
            result = Optional.ofNullable(accidents.get(id));
        }
        return result;
    }

    public List<Accident> findByName(String key) {
        List<Accident> list = new ArrayList<>();
        for (Map.Entry<Integer, Accident> entry : accidents.entrySet()) {
            if (entry.getValue().getName().matches(key)) {
                list.add(entry.getValue());
            }
        }
        return list;
    }

    public boolean replace(int id, Accident accident) {
        return accidents.replace(id, accident) != null;
    }

    public boolean delete(int id) {
        return accidents.remove(id) != null;
    }
}

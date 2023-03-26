package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository {
    public Accident add(Accident accident);

    public List<Accident> findAll();

    public Optional<Accident> findById(int id);

    public List<Accident> findByName(String key);

    public boolean replace(int id, Accident accident);

    public boolean delete(int id);
}

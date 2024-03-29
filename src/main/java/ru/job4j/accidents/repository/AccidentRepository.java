package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

public interface AccidentRepository {
    public Optional<Accident> add(Accident accident);

    public List<Accident> findAll();

    public Optional<Accident> findById(int id);

    public boolean replace(Accident accident);

    public boolean delete(int id);
}

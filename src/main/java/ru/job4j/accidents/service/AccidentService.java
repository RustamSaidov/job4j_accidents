package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;


public interface AccidentService {
    public Optional<Accident> create(Accident accident);

    public List<Accident> findAll();

    public Optional<Accident> findById(int id);

    public boolean replace(Accident accident);

    public boolean delete(int id);
}

package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

public interface AccidentTypeRepository {
    public AccidentType add(AccidentType accidentType);

    public List<AccidentType> findAll();

    public Optional<AccidentType> findById(int id);

    public boolean replace(int id, AccidentType accidentType);

    public boolean delete(int id);
}

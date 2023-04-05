package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.List;

public interface AccidentJPARepository extends CrudRepository<Accident, Integer> {

    public default List<Accident> getAll() {
        return (List<Accident>) findAll();
    }
}
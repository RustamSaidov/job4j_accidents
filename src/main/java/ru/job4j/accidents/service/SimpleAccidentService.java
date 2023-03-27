package ru.job4j.accidents.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleAccidentService implements AccidentService {
    private final AccidentRepository accidentRepository;

    public SimpleAccidentService(AccidentRepository accidentMem) {
        this.accidentRepository = accidentMem;
    }


    @Override
    public Accident create(Accident accident) {
        return accidentRepository.add(accident);
    }

    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public boolean replace(int id, Accident accident) {
        return accidentRepository.replace(id, accident);
    }

    @Override
    public boolean delete(int id) {
        return accidentRepository.delete(id);
    }
}

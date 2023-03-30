package ru.job4j.accidents.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleAccidentService implements AccidentService {
    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;

    public SimpleAccidentService(AccidentRepository accidentMem,AccidentTypeRepository accidentTypeRepository) {
        this.accidentRepository = accidentMem;
        this.accidentTypeRepository = accidentTypeRepository;
    }


    @Override
    public Accident create(Accident accident) {
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
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
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        return accidentRepository.replace(id, accident);
    }

    @Override
    public boolean delete(int id) {
        return accidentRepository.delete(id);
    }
}

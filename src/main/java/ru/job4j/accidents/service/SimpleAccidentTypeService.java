package ru.job4j.accidents.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleAccidentTypeService implements AccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    public SimpleAccidentTypeService(AccidentTypeRepository accidentTypeMem) {
        this.accidentTypeRepository = accidentTypeMem;
    }


    @Override
    public AccidentType create(AccidentType accidentType) {
        return accidentTypeRepository.add(accidentType);
    }

    @Override
    public List<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }

    @Override
    public boolean replace(int id, AccidentType accidentType) {
        return accidentTypeRepository.replace(id, accidentType);
    }

    @Override
    public boolean delete(int id) {
        return accidentTypeRepository.delete(id);
    }
}

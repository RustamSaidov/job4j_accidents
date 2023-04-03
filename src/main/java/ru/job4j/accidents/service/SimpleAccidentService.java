package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentHibernate;
import ru.job4j.accidents.repository.AccidentJdbcTemplate;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {
    private final AccidentHibernate accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public Accident create(Accident accident) {
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        return accidentRepository.add(accident);
    }

    @Override
    public List<Accident> findAll() {
        var list = accidentRepository.findAll();
        for (int i = 0; i < list.size(); i++) {
            var accident = list.get(i);
            accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        }
        return list;
    }

    @Override
    public Optional<Accident> findById(int id) {
        var accident = accidentRepository.findById(id);
        if (accident.isPresent()) {
            accident.get().setType(accidentTypeRepository.findById(accident.get().getType().getId()).get());
        }
        return accident;
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

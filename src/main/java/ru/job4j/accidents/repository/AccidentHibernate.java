package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
@Primary
public class AccidentHibernate implements AccidentRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param accident происшествие.
     * @return Optional of accident.
     */
    public Optional<Accident> add(Accident accident) {
        try {
            crudRepository.run(session -> session.persist(accident));
        } catch (Exception exception) {
            return Optional.empty();
        }
        return Optional.ofNullable(accident);
    }

    /**
     * Удалить происшествие по id.
     *
     * @return boolean.
     */
    public boolean delete(int accidentId) {
        try {
            crudRepository.run(
                    "delete from Accident where id = :fId",
                    Map.of("fId", accidentId));
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    /**
     * Обновить в базе происшествие.
     *
     * @param accident задание.
     * @return boolean.
     */
    public boolean replace(Accident accident) {
        try {
            crudRepository.run(session -> session.merge(accident));
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    /**
     * Найти происшествие по id.
     *
     * @param id ID.
     * @return Optional of accident.
     */
    @Override
    public Optional<Accident> findById(int id) {
        Optional<Accident> result = Optional.empty();
        try {
            result = crudRepository.optional("""
                             from Accident as a WHERE a.id = :fId
                            """, Accident.class,
                    Map.of("fId", id));
        } catch (Exception exception) {
            return result;
        }
        return result;
    }

    /**
     * Список происшествий отсортированных по id.
     *
     * @return список происшествий.
     */
    @Override
    public List<Accident> findAll() {
        var list = crudRepository.query("FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules ORDER BY a.id", Accident.class);
        return list.stream().distinct().collect(Collectors.toList());
    }

}
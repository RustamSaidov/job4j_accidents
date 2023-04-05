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
public class AccidentHibernate {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param accident происшествие.
     * @return Optional of accident.
     */
    public Optional<Accident> add(Accident accident) {
        crudRepository.run(session -> session.persist(accident));
        return Optional.ofNullable(accident);
    }

    /**
     * Удалить происшествие по id.
     *
     * @return boolean.
     */
    public boolean delete(int accidentId) {
        var result = crudRepository.optional("""
                         delete from Accident where id = :fId
                        """, Accident.class,
                Map.of("fId", accidentId));
        return result.isEmpty();
    }

    /**
     * Обновить в базе происшествие.
     *
     * @param accident задание.
     */
    public void replace(Accident accident) {
        crudRepository.run(session -> session.merge(accident));
    }

    /**
     * Найти происшествие по id.
     *
     * @param id ID.
     * @return Optional of accident.
     */

    public Optional<Accident> findById(int id) {
        return crudRepository.optional("""
                         from Accident as a WHERE a.id = :fId
                        """, Accident.class,
                Map.of("fId", id));
    }

    /**
     * Список происшествий отсортированных по id.
     *
     * @return список происшествий.
     */

    public List<Accident> findAll() {
        var list = crudRepository.query("FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules ORDER BY a.id", Accident.class);
        return list.stream().distinct().collect(Collectors.toList());
    }

}
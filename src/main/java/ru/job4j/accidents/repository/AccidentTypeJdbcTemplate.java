package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
public class AccidentTypeJdbcTemplate implements AccidentTypeRepository {

    private final JdbcTemplate jdbc;

    public AccidentType add(AccidentType accidentType) {
        jdbc.update("insert into accident_types (name) values (?)",
                accidentType.getName());
        return accidentType;
    }

    public List<AccidentType> findAll() {
        return jdbc.query("select id, name from accident_types",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                });
    }

    @Override
    public Optional<AccidentType> findById(int id) {

        Optional<AccidentType> result = Optional.empty();
        AccidentType accidentType = jdbc.queryForObject(
                "select id, name from accident_types where id = ?",
                (resultSet, rowNum) -> {
                    AccidentType newAccidentType = new AccidentType();
                    newAccidentType.setId(resultSet.getInt("id"));
                    newAccidentType.setName(resultSet.getString("name"));
                    return newAccidentType;
                },
                id);
        if (accidentType != null) {
            result = Optional.of(accidentType);
        }
        return result;
    }

    @Override
    public boolean replace(int id, AccidentType accidentType) {
        return jdbc.update(
                "update accident_types set name = ? where id = ?",
                accidentType.getName(), id)
                != 0;
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update(
                "delete from accident_types where id = ?", id)
                != 0;
    }
}


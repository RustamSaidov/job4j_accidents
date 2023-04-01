package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

    private final JdbcTemplate jdbc;

    public Accident add(Accident accident) {
        jdbc.update("insert into accidents (name) values (?, ?, ?, ? )",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType());
        return accident;
    }

    public List<Accident> findAll() {
        return jdbc.query("select id, name, text, address, type_id from accidents",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("type_id"));
                    accident.setType(accidentType);
                    return accident;
                });
    }

    @Override
    public Optional<Accident> findById(int id) {

        Optional<Accident> result = Optional.empty();
        Accident accident = jdbc.queryForObject(
                "select id, name, text, address, type_id from accidents where id = ?",
                (resultSet, rowNum) -> {
                    Accident newAccident = new Accident();
                    newAccident.setId(resultSet.getInt("id"));
                    newAccident.setName(resultSet.getString("name"));
                    newAccident.setText(resultSet.getString("text"));
                    newAccident.setAddress(resultSet.getString("address"));
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(resultSet.getInt("type_id"));
                    newAccident.setType(accidentType);
                    return newAccident;
                },
                id);
        if (accident != null) {
            result = Optional.of(accident);
        }
        return result;
    }

    @Override
    public boolean replace(int id, Accident accident) {
        return jdbc.update(
                "update accidents set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId(), id)
                != 0;
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update(
                "delete from accidents where id = ?", id)
                != 0;
    }
}

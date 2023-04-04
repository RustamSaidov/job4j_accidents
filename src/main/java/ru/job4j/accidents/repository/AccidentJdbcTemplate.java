package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

    private final JdbcTemplate jdbc;
    private final AccidentRuleJdbcTemplate accidentRuleJdbcTemplate;

    public Optional<Accident> add(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into accidents (name, text, address, type_id) values (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        accident.setId((int) keyHolder.getKeys().get("id"));
        accidentRuleJdbcTemplate.add(accident.getId(), accident.getRules());
        return Optional.ofNullable(accident);
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
                    accident.setRules(accidentRuleJdbcTemplate.findById(rs.getInt("id")));
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
                    newAccident.setRules(accidentRuleJdbcTemplate.findById(resultSet.getInt("id")));
                    return newAccident;
                },
                id);
        if (accident != null) {
            result = Optional.of(accident);
        }
        return result;
    }

    @Override
    public boolean replace(Accident accident) {
        accidentRuleJdbcTemplate.replace(accident.getId(), accident);
        return jdbc.update(
                "update accidents set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId(), accident.getId())
                != 0;
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update(
                "delete from accidents where id = ?", id)
                != 0;
    }
}

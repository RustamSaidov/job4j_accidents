package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate implements RuleRepository {
    private final JdbcTemplate jdbc;

    public Rule add(Rule rule) {
        jdbc.update("insert into rules_table (name) values (?)",
                rule.getName());
        return rule;
    }

    public List<Rule> findAll() {
        return jdbc.query("select id, name from rules_table",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    @Override
    public Optional<Rule> findById(int id) {
        Optional<Rule> result = Optional.empty();
        Rule rule = jdbc.queryForObject(
                "select id, name from rules_table where id = ?",
                (resultSet, rowNum) -> {
                    Rule newRule = new Rule();
                    newRule.setId(resultSet.getInt("id"));
                    newRule.setName(resultSet.getString("name"));
                    return newRule;
                },
                id);
        if (rule != null) {
            result = Optional.of(rule);
        }
        return result;
    }

    @Override
    public boolean replace(int id, Rule rule) {
        return jdbc.update(
                "update rules_table set name = ? where id = ?",
                rule.getName(), id)
                != 0;
    }

    @Override
    public boolean delete(int id) {
        return jdbc.update(
                "delete from rules_table where id = ?", id)
                != 0;
    }
}

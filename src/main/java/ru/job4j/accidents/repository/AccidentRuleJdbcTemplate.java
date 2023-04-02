package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Primary
@Repository
@AllArgsConstructor
public class AccidentRuleJdbcTemplate {

    private final JdbcTemplate jdbc;
    private final RuleRepository ruleRepository;

    public Set<Rule> add(int accId, Set<Rule> ruleSet) {
        for (Rule rule : ruleSet) {
            jdbc.update("insert into accident_rules (accident_id, rules_id) values (?, ?)",
                    accId, rule.getId());
        }
        return ruleSet;
    }

    public Set<Rule> findById(int id) {
        List<Rule> listRule = jdbc.query("select rules_id from accident_rules where accident_id = ?",
                (rs, row) -> {
                    Rule rule = ruleRepository.findById(rs.getInt("rules_id")).get();
                    return rule;
                }, id);
        return new HashSet<Rule>(listRule);


    }

    public Set<Rule> replace(int id, Accident accident) {
        delete(id);
        return add(id, accident.getRules());
    }

    public boolean delete(int id) {
        return jdbc.update(
                "delete from accident_rules where accident_id = ?", id)
                != 0;
    }
}

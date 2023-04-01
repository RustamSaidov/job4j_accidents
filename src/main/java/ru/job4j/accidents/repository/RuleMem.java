package ru.job4j.accidents.repository;


import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class RuleMem implements RuleRepository {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger();

    public RuleMem() {
        add(new Rule(1, "Статья 1"));
        add(new Rule(2, "Статья 2"));
        add(new Rule(3, "Статья 3.1"));
        add(new Rule(4, "Статья 3.2"));
    }

    public Rule add(Rule accidentType) {
        accidentType.setId(id.incrementAndGet());
        rules.put(accidentType.getId(), accidentType);
        return accidentType;
    }

    public List<Rule> findAll() {
        return new ArrayList<Rule>(rules.values());
    }

    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    public boolean replace(int id, Rule accidentType) {
        return rules.replace(id, accidentType) != null;
    }

    public boolean delete(int id) {
        return rules.remove(id) != null;
    }
}

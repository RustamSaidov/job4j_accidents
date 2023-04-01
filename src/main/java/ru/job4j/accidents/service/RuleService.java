package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RuleService {
    public Rule create(Rule rule);

    public List<Rule> findAll();

    public Optional<Rule> findById(int id);

    public boolean replace(int id, Rule rule);

    public boolean delete(int id);

    public Set<Rule> getSetRuleByIdArray(String[] ids);
}

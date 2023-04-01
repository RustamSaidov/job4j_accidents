package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;


public interface RuleRepository {
    public Rule add(Rule rule);

    public List<Rule> findAll();

    public Optional<Rule> findById(int id);

    public boolean replace(int id, Rule rule);

    public boolean delete(int id);
}


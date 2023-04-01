package ru.job4j.accidents.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ThreadSafe
@Service
public class SimpleRuleService implements RuleService {
    private final RuleRepository ruleRepository;

    public SimpleRuleService(RuleRepository ruleMem) {
        this.ruleRepository = ruleMem;
    }


    @Override
    public Rule create(Rule rule) {
        return ruleRepository.add(rule);
    }

    @Override
    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    @Override
    public boolean replace(int id, Rule rule) {
        return ruleRepository.replace(id, rule);
    }

    @Override
    public boolean delete(int id) {
        return ruleRepository.delete(id);
    }

    @Override
    public Set<Rule> getSetRuleByIdArray(String[] ids) {
        Set<Rule> rules = new HashSet<>();
        for (int i = 0; i < ids.length; i++) {
            rules.add(ruleRepository.findById(Integer.parseInt(ids[i])).get());
        }
        return rules;
    }
}

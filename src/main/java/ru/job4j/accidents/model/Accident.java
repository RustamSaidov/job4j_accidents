package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accidents")

/*
Исключение LazyInitialization Решил вопрос через аннотацию @Transactional в тестовых классах.
@NamedEntityGraph(
        name = "accident-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("type"),
                @NamedAttributeNode("rules")
        }
)*/
public class Accident {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String text;
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private AccidentType type;
    @ManyToMany
    @JoinTable(
            name = "accident_rules",
            joinColumns = {@JoinColumn(name = "accident_id")},
            inverseJoinColumns = {@JoinColumn(name = "rule_id")}
    )
    private Set<Rule> rules;
}


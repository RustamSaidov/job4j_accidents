package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
public class RuleHibernate implements RuleRepository {
    private final SessionFactory sf;

    @Override
    public Rule add(Rule rule) {
        try (Session session = sf.openSession()) {
            session.save(rule);
            return rule;
        }
    }

    @Override
    public List<Rule> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Rule", Rule.class)
                    .list();
        }
    }

    @Override
    public Optional<Rule> findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Rule result = session.get(Rule.class, id);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(result);
    }

    @Override
    public boolean replace(int id, Rule rule) {
        boolean result = true;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("""
                            UPDATE Rule
                            SET name = :name
                            WHERE id = :id
                            """)
                    .setParameter("name", rule.getName())
                    .setParameter("id", rule.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            result = false;
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = true;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Rule WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            result = false;
        }
        session.close();
        return result;
    }
}

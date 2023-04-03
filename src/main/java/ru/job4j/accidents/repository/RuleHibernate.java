package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Primary
public class RuleHibernate implements RuleRepository{
    private final SessionFactory sf;
//    private final AccidentRuleJdbcTemplate accidentRuleJdbcTemplate;

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
            result=false;
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
            result=false;
        }
        session.close();
        return result;
    }

//    @Override
//    public Accident add(Accident accident) {
//        Session session = sf.openSession();
//        try {
//            session.beginTransaction();
//            session.createQuery("""
//                    INSERT INTO Accident
//                    (name, text, address, type_id)
//                    VALUES(:name, :text, :address, :type_id)
//                    """)
//                    .setParameter("name", accident.getName())
//                    .setParameter("text", accident.getText())
//                    .setParameter("address", accident.getAddress())
//                    .setParameter("type_id", accident.getType().getId())
//                    .executeUpdate();
//            session.getTransaction().commit();
//            accident = session.get(Accident.class, accident.getId());
//            accidentRuleJdbcTemplate.add(accident.getId(), accident.getRules());
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//        }
//        return accident;
//    }



//    @Override
//    public Task save(Task task) {
//        Session session = sf.openSession();
//        try {
//            session.beginTransaction();
//            session.save(task);
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//        }
//        session.close();
//        return task;
//    }
//
//    @Override
//    public void deleteById(int id) {
//        Session session = sf.openSession();
//        try {
//            session.beginTransaction();
//            session.createQuery(
//                            "DELETE Task WHERE id = :fId")
//                    .setParameter("fId", id)
//                    .executeUpdate();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//        }
//        session.close();
//    }
//
//    @Override
//    public void update(Task task) {
//        Session session = sf.openSession();
//        try {
//            session.beginTransaction();
//            session.createQuery("""
//                    UPDATE Task
//                    SET description = :description, created = :created,
//                        done = :done
//                    WHERE id = :id
//                    """)
//                    .setParameter("description", task.getDescription())
//                    .setParameter("created", task.getCreated())
//                    .setParameter("done", task.isDone())
//                    .setParameter("fId", task.getId())
//                    .executeUpdate();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//        }
//    }
//
//    @Override
//    public Optional<Task> findById(int id) {
//        Session session = sf.openSession();
//        session.beginTransaction();
//        Task result = session.get(Task.class, id);
//        session.getTransaction().commit();
//        session.close();
//        return Optional.ofNullable(result);
//    }
//
//    @Override
//    public Collection<Task> findAll() {
//        Session session = sf.openSession();
//        session.beginTransaction();
//        List<Task> result = session.createQuery("from Task ORDER BY id", Task.class).list();
//        session.getTransaction().commit();
//        session.close();
//        return result;
//    }
}

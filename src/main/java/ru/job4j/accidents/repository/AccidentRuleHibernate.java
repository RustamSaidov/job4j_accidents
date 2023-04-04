package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
@Primary
public class AccidentRuleHibernate implements AccidentRepository {
    private final SessionFactory sf;
    private final AccidentRuleJdbcTemplate accidentRuleJdbcTemplate;


    @Override
    public Accident add(Accident accident) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(accident);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return accident;
    }

    @Override
    public boolean replace(int id, Accident accident) {
        boolean result = true;
        Session session = sf.openSession();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        try {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            session.beginTransaction();
            session.createQuery("""
                            UPDATE Accident
                            SET name = :name, text = :text, address = :address, type_id = :type_id
                            WHERE id = :id
                            """)
                    .setParameter("name", accident.getName())
                    .setParameter("text", accident.getText())
                    .setParameter("address", accident.getAddress())
                    .setParameter("type_id", accident.getType().getId())
                    .setParameter("id", accident.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            accidentRuleJdbcTemplate.replace(id, accident);
        } catch (Exception e) {
            session.getTransaction().rollback();
            result = false;
        }
        return result;
    }

    @Override
    public List<Accident> findAll() {
        try (Session session = sf.openSession()) {
            var result = session
                    .createQuery("FROM Accident a JOIN FETCH a.type JOIN FETCH a.rules ORDER BY a.id", Accident.class)
                    .list();
            List<Accident> resultWithoutDuplicates = result.stream()
                    .distinct()
                    .collect(Collectors.toList());
            return resultWithoutDuplicates;
        }
    }

    @Override
    public Optional<Accident> findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Accident result = session.get(Accident.class, id);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(result);
    }

    @Override
    public boolean delete(int id) {
        boolean result = true;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Accident WHERE id = :fId")
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
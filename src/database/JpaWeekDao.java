package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import fantasyuser.Week;

public class JpaWeekDao extends Dao<Week> {
    @Override
    public Optional<Week> get(long id) {
        return Optional.ofNullable(entityManager.find(Week.class, id));
    }

    @Override
    public List<Week> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Week e");
        return query.getResultList();
    }

    @Override
    public void save(Week week) {
        executeInsideTransaction(entityManager -> entityManager.persist(week));
    }

    @Override
    public void update(Week week) {
        executeInsideTransaction(entityManager -> entityManager.merge(week));
    }

    @Override
    public void delete(Week week) {
        executeInsideTransaction(entityManager -> entityManager.remove(week));
    }
}
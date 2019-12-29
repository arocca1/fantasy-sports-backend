package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import athlete.Sport;

public class JpaSportDao extends Dao<Sport> {
    @Override
    public Optional<Sport> get(long id) {
        return Optional.ofNullable(entityManager.find(Sport.class, id));
    }

    @Override
    public List<Sport> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM sports e");
        return query.getResultList();
    }

    @Override
    public void save(Sport sport) {
        executeInsideTransaction(entityManager -> entityManager.persist(sport));
    }

    @Override
    public void update(Sport sport) {
        executeInsideTransaction(entityManager -> entityManager.merge(sport));
    }

    @Override
    public void delete(Sport sport) {
        executeInsideTransaction(entityManager -> entityManager.remove(sport));
    }
}
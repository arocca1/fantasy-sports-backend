package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import fantasyuser.Division;

public class JpaDivisionDao extends Dao<Division> {
    @Override
    public Optional<Division> get(long id) {
        return Optional.ofNullable(entityManager.find(Division.class, id));
    }

    @Override
    public List<Division> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Division e");
        return query.getResultList();
    }

    @Override
    public void save(Division division) {
        executeInsideTransaction(entityManager -> entityManager.persist(division));
    }

    @Override
    public void update(Division division) {
        executeInsideTransaction(entityManager -> entityManager.merge(division));
    }

    @Override
    public void delete(Division division) {
        executeInsideTransaction(entityManager -> entityManager.remove(division));
    }
}
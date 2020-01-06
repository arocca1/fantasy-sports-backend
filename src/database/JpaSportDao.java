package database;

import java.util.Calendar;
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
        Query query = entityManager.createQuery("SELECT e FROM Sport e");
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

    public Optional<Sport> get(String name) {
    	String q = "SELECT s FROM Sport s WHERE s.name = :name";
    	Query query = entityManager.createQuery(q);
    	query.setParameter("name", name);
    	List<Sport> results = query.getResultList();
    	return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
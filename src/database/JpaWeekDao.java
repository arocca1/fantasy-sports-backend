package database;

import java.util.Calendar;
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

    public Optional<Week> getCurrentWeek(long seasonId) {
    	String q = "SELECT MIN(w.id) FROM Week w JOIN w.season s WHERE s.id = :seasonId AND s.startDate >= :startDate";
    	Query query = entityManager.createQuery(q);
    	query.setParameter("seasonId", seasonId);
    	query.setParameter("startDate", Calendar.getInstance().getTime());
    	List<Week> results = query.getResultList();
    	return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import athlete.Player;
import athlete.Position;

public class JpaPositionDao extends Dao<Position> {
    @Override
    public Optional<Position> get(long id) {
        return Optional.ofNullable(entityManager.find(Position.class, id));
    }

    @Override
    public List<Position> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Position e");
        return query.getResultList();
    }

    @Override
    public void save(Position position) {
        executeInsideTransaction(entityManager -> entityManager.persist(position));
    }

    @Override
    public void update(Position position) {
        executeInsideTransaction(entityManager -> entityManager.merge(position));
    }

    @Override
    public void delete(Position position) {
        executeInsideTransaction(entityManager -> entityManager.remove(position));
    }

    public List<Position> getPositionsForSport(long sportId) {
    	String q = "SELECT p FROM Position p JOIN p.sport s WHERE s.id = :sportId";
    	Query query = entityManager.createQuery(q);
    	query.setParameter("sportId", sportId);
    	return query.getResultList();
    }
}
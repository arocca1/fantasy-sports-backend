package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import athlete.RealLeague;
import athlete.Sport;

public class JpaRealLeagueDao extends Dao<RealLeague> {
    @Override
    public Optional<RealLeague> get(long id) {
        return Optional.ofNullable(entityManager.find(RealLeague.class, id));
    }

    @Override
    public List<RealLeague> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM RealLeague e");
        return query.getResultList();
    }

    @Override
    public void save(RealLeague realLeague) {
        executeInsideTransaction(entityManager -> entityManager.persist(realLeague));
    }

    @Override
    public void update(RealLeague realLeague) {
        executeInsideTransaction(entityManager -> entityManager.merge(realLeague));
    }

    @Override
    public void delete(RealLeague realLeague) {
        executeInsideTransaction(entityManager -> entityManager.remove(realLeague));
    }

    public Optional<RealLeague> get(long sportId, String name) {
    	String q = "SELECT rl FROM RealLeague rl JOIN rl.sport s WHERE s.id = :sportId AND rl.name = :name";
    	Query query = entityManager.createQuery(q);
    	query.setParameter("sportId", sportId);
    	query.setParameter("name", name);
    	List<RealLeague> results = query.getResultList();
    	return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
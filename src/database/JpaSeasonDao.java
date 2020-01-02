package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import fantasyuser.Season;

public class JpaSeasonDao extends Dao<Season> {
    @Override
    public Optional<Season> get(long id) {
        return Optional.ofNullable(entityManager.find(Season.class, id));
    }

    @Override
    public List<Season> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Season e");
        return query.getResultList();
    }

    @Override
    public void save(Season season) {
        executeInsideTransaction(entityManager -> entityManager.persist(season));
    }

    @Override
    public void update(Season season) {
        executeInsideTransaction(entityManager -> entityManager.merge(season));
    }

    @Override
    public void delete(Season season) {
        executeInsideTransaction(entityManager -> entityManager.remove(season));
    }

    public Optional<Season> get(long leagueId, int year) {
    	String q = "SELECT s FROM Season s JOIN s.league l WHERE l.id = :leagueId AND s.year = :year";
    	Query query = entityManager.createQuery(q);
    	query.setParameter("leagueId", leagueId);
    	query.setParameter("year", year);
    	List<Season> results = query.getResultList();
    	return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
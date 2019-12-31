package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import fantasyuser.WeeklyScore;

public class JpaWeeklyScoreDao extends Dao<WeeklyScore> {
    @Override
    public Optional<WeeklyScore> get(long id) {
        return Optional.ofNullable(entityManager.find(WeeklyScore.class, id));
    }

    @Override
    public List<WeeklyScore> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM WeeklyScore e");
        return query.getResultList();
    }

    @Override
    public void save(WeeklyScore weeklyScore) {
        executeInsideTransaction(entityManager -> entityManager.persist(weeklyScore));
    }

    @Override
    public void update(WeeklyScore weeklyScore) {
        executeInsideTransaction(entityManager -> entityManager.merge(weeklyScore));
    }

    @Override
    public void delete(WeeklyScore weeklyScore) {
        executeInsideTransaction(entityManager -> entityManager.remove(weeklyScore));
    }

    public Optional<Double> getProjectedScore(long leagueId, int week, long playerId) {
    	String q = "SELECT ws.projectedScore FROM WeeklyScore ws JOIN ws.fantasyPlayer fp JOIN ws.week w JOIN w.season s JOIN s.league l WHERE l.id = :leagueId AND w.num = :week AND fp.id = :playerId";
    	Query query = entityManager.createQuery(q);
    	query.setParameter("leagueId", leagueId);
    	query.setParameter("week", week);
    	query.setParameter("playerId", playerId);
    	List<Double> results = query.getResultList();
    	return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Optional<Double> getActualScore(long leagueId, int week, long playerId) {
    	String q = "SELECT ws.actualScore FROM WeeklyScore ws JOIN ws.fantasyPlayer fp JOIN ws.week w JOIN w.season s JOIN s.league l WHERE l.id = :leagueId AND w.num = :week AND fp.id = :playerId";
    	Query query = entityManager.createQuery(q);
    	query.setParameter("leagueId", leagueId);
    	query.setParameter("week", week);
    	query.setParameter("playerId", playerId);
    	List<Double> results = query.getResultList();
    	return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
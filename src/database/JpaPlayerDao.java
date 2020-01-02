package database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import athlete.Player;

public class JpaPlayerDao extends Dao<Player> {
    @Override
    public Optional<Player> get(long id) {
        return Optional.ofNullable(entityManager.find(Player.class, id));
    }

    @Override
    public List<Player> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Player e");
        return query.getResultList();
    }

    @Override
    public void save(Player player) {
        executeInsideTransaction(entityManager -> entityManager.persist(player));
    }

    @Override
    public void update(Player player) {
        executeInsideTransaction(entityManager -> entityManager.merge(player));
    }

    @Override
    public void delete(Player player) {
        executeInsideTransaction(entityManager -> entityManager.remove(player));
    }

    public List<Player> getStarters(long teamId, long weekId) {
    	String q = "SELECT p FROM PlayerTeamRecord ptr JOIN ptr.player p JOIN ptr.team t JOIN ptr.week w WHERE t.id = :teamId AND w.id = :weekId AND ptr.isStarter";
    	Query query = entityManager.createQuery(q);
    	query.setParameter("teamId", teamId);
    	query.setParameter("weekId", weekId);
    	return query.getResultList();
    }

    public List<Player> getStarters(long teamId, long seasonId, int week) {
    	String q = "SELECT p FROM PlayerTeamRecord ptr JOIN ptr.player p JOIN ptr.team t JOIN ptr.week w JOIN w.season s WHERE t.id = :teamId AND s.id = :seasonId AND w.num = :week AND ptr.isStarter";
    	Query query = entityManager.createQuery(q);
    	query.setParameter("teamId", teamId);
    	query.setParameter("seasonId", seasonId);
    	query.setParameter("week", week);
    	return query.getResultList();
    }

    public List<Player> getFullTeam(long teamId, long weekId) {
    	String q = "SELECT p FROM PlayerTeamRecord ptr JOIN ptr.player p JOIN ptr.team t JOIN ptr.week w WHERE t.id = :teamId AND w.id = :weekId";
    	Query query = entityManager.createQuery(q);
    	query.setParameter("teamId", teamId);
    	query.setParameter("weekId", weekId);
    	return query.getResultList();
    }

    public List<Player> getFullTeam(long teamId, long seasonId, int week) {
    	String q = "SELECT p FROM PlayerTeamRecord ptr JOIN ptr.player p JOIN ptr.team t JOIN ptr.week w JOIN w.season s WHERE t.id = :teamId AND s.id = :seasonId AND w.num = :week";
    	Query query = entityManager.createQuery(q);
    	query.setParameter("teamId", teamId);
    	query.setParameter("seasonId", seasonId);
    	query.setParameter("week", week);
    	return query.getResultList();
    }

    public List<Player> getCurrentFullTeam(long teamId, long seasonId) {
    	// get the current week
    	String q1 = "SELECT MIN(w.id) FROM Week w JOIN w.season s WHERE s.id = :seasonId AND s.startDate >= :startDate";
    	Query query1 = entityManager.createQuery(q1);
    	query1.setParameter("seasonId", seasonId);
    	query1.setParameter("startDate", Calendar.getInstance().getTime());
    	List<Long> results = query1.getResultList();
    	return results.isEmpty() ? new ArrayList<Player>() : getFullTeam(teamId, results.get(0));
    }
}
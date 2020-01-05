package database;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import athlete.Player;
import fantasyuser.Team;

public class JpaTeamDao extends Dao<Team> {
    @Override
    public Optional<Team> get(long id) {
        return Optional.ofNullable(entityManager.find(Team.class, id));
    }

    @Override
    public List<Team> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Team e");
        return query.getResultList();
    }

    @Override
    public void save(Team team) {
        executeInsideTransaction(entityManager -> entityManager.persist(team));
    }

    @Override
    public void update(Team team) {
        executeInsideTransaction(entityManager -> entityManager.merge(team));
    }

    @Override
    public void delete(Team team) {
        executeInsideTransaction(entityManager -> entityManager.remove(team));
    }

    public int getNumPlayersOnTeam(long teamId, long weekId) {
    	String q = "SELECT COUNT(p) FROM PlayerTeamRecord ptr JOIN ptr.player p JOIN ptr.team t JOIN ptr.week w WHERE t.id = :teamId AND w.id = :weekId";
    	Query query = entityManager.createQuery(q);
    	query.setParameter("teamId", teamId);
    	query.setParameter("weekId", weekId);
    	List<Integer> results = query.getResultList();
    	return results.isEmpty() ? 0 : results.get(0);
    }
}
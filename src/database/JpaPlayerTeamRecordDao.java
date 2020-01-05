package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import fantasyuser.PlayerTeamRecord;

public class JpaPlayerTeamRecordDao extends Dao<PlayerTeamRecord> {
    @Override
    public Optional<PlayerTeamRecord> get(long id) {
        return Optional.ofNullable(entityManager.find(PlayerTeamRecord.class, id));
    }

    @Override
    public List<PlayerTeamRecord> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM PlayerTeamRecord e");
        return query.getResultList();
    }

    @Override
    public void save(PlayerTeamRecord player) {
        executeInsideTransaction(entityManager -> entityManager.persist(player));
    }

    @Override
    public void update(PlayerTeamRecord player) {
        executeInsideTransaction(entityManager -> entityManager.merge(player));
    }

    @Override
    public void delete(PlayerTeamRecord player) {
        executeInsideTransaction(entityManager -> entityManager.remove(player));
    }

    public Optional<PlayerTeamRecord> get(long playerId, long teamId, long weekId) {
    	Query query = entityManager.createQuery("SELECT ptr FROM PlayerTeamRecord ptr JOIN ptr.player p JOIN ptr.team t JOIN ptr.week w WHERE p.id = :playerId AND t.id = :teamId AND w.id = :weekId");
    	query.setParameter("playerId", playerId);
    	query.setParameter("teamId", teamId);
    	query.setParameter("weekId", weekId);
        List<PlayerTeamRecord> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Optional<PlayerTeamRecord> get(long playerId, long weekId) {
    	Query query = entityManager.createQuery("SELECT ptr FROM PlayerTeamRecord ptr JOIN ptr.player p JOIN ptr.week w WHERE p.id = :playerId AND w.id = :weekId");
    	query.setParameter("playerId", playerId);
    	query.setParameter("weekId", weekId);
        List<PlayerTeamRecord> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public int numStartersInPosition(long teamId, long weekId, long positionId) {
    	Query query = entityManager.createQuery("SELECT COUNT(ptr) FROM PlayerTeamRecord ptr JOIN ptr.player p JOIN p.position pos JOIN ptr.team t JOIN ptr.week w WHERE pos.id = :positionId AND t.id = :teamId AND w.id = :weekId");
    	query.setParameter("positionId", positionId);
    	query.setParameter("teamId", teamId);
    	query.setParameter("weekId", weekId);
        List<Integer> results = query.getResultList();
        return results.isEmpty() ? 0 : results.get(0);
    }
}
package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import fantasyuser.PlayerTeamRecord;
import fantasyuser.SeasonLineupPositionRequirement;

public class JpaSeasonLineupPositionRequirementDao extends Dao<SeasonLineupPositionRequirement> {
    @Override
    public Optional<SeasonLineupPositionRequirement> get(long id) {
        return Optional.ofNullable(entityManager.find(SeasonLineupPositionRequirement.class, id));
    }

    @Override
    public List<SeasonLineupPositionRequirement> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM SeasonLineupPositionRequirement e");
        return query.getResultList();
    }

    @Override
    public void save(SeasonLineupPositionRequirement requirement) {
        executeInsideTransaction(entityManager -> entityManager.persist(requirement));
    }

    @Override
    public void update(SeasonLineupPositionRequirement requirement) {
        executeInsideTransaction(entityManager -> entityManager.merge(requirement));
    }

    @Override
    public void delete(SeasonLineupPositionRequirement requirement) {
        executeInsideTransaction(entityManager -> entityManager.remove(requirement));
    }

    public Optional<SeasonLineupPositionRequirement> getForSeasonAndPosition(long seasonId, long positionId) {
    	Query query = entityManager.createQuery("SELECT slpr FROM SeasonLineupPositionRequirement slpr JOIN slpr.season s JOIN slpr.position p WHERE s.id = :seasonId AND p.id = :positionId");
    	query.setParameter("seasonId", seasonId);
    	query.setParameter("positionId", positionId);
        List<SeasonLineupPositionRequirement> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
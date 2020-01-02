package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

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
}
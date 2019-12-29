package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import fantasyuser.Matchup;

public class JpaMatchupDao extends Dao<Matchup> {
    @Override
    public Optional<Matchup> get(long id) {
        return Optional.ofNullable(entityManager.find(Matchup.class, id));
    }

    @Override
    public List<Matchup> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Matchup e");
        return query.getResultList();
    }

    @Override
    public void save(Matchup matchup) {
        executeInsideTransaction(entityManager -> entityManager.persist(matchup));
    }

    @Override
    public void update(Matchup matchup) {
        executeInsideTransaction(entityManager -> entityManager.merge(matchup));
    }

    @Override
    public void delete(Matchup matchup) {
        executeInsideTransaction(entityManager -> entityManager.remove(matchup));
    }
}
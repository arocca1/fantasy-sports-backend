package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import fantasyuser.League;

public class JpaLeagueDao extends Dao<League> {
    @Override
    public Optional<League> get(long id) {
        return Optional.ofNullable(entityManager.find(League.class, id));
    }

    @Override
    public List<League> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM leagues e");
        return query.getResultList();
    }

    @Override
    public void save(League league) {
        executeInsideTransaction(entityManager -> entityManager.persist(league));
    }

    @Override
    public void update(League league) {
        executeInsideTransaction(entityManager -> entityManager.merge(league));
    }

    @Override
    public void delete(League league) {
        executeInsideTransaction(entityManager -> entityManager.remove(league));
    }
}

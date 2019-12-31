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
}
package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import athlete.RealLeague;

public class JpaRealLeagueDao extends Dao<RealLeague> {
    @Override
    public Optional<RealLeague> get(long id) {
        return Optional.ofNullable(entityManager.find(RealLeague.class, id));
    }

    @Override
    public List<RealLeague> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM real_leagues e");
        return query.getResultList();
    }

    @Override
    public void save(RealLeague realLeague) {
        executeInsideTransaction(entityManager -> entityManager.persist(realLeague));
    }

    @Override
    public void update(RealLeague realLeague) {
        executeInsideTransaction(entityManager -> entityManager.merge(realLeague));
    }

    @Override
    public void delete(RealLeague realLeague) {
        executeInsideTransaction(entityManager -> entityManager.remove(realLeague));
    }
}
package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import fantasyuser.FantasyPlayer;

public class JpaFantasyPlayerDao extends Dao<FantasyPlayer> {
    @Override
    public Optional<FantasyPlayer> get(long id) {
        return Optional.ofNullable(entityManager.find(FantasyPlayer.class, id));
    }

    @Override
    public List<FantasyPlayer> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM FantasyPlayer e");
        return query.getResultList();
    }

    @Override
    public void save(FantasyPlayer player) {
        executeInsideTransaction(entityManager -> entityManager.persist(player));
    }

    @Override
    public void update(FantasyPlayer player) {
        executeInsideTransaction(entityManager -> entityManager.merge(player));
    }

    @Override
    public void delete(FantasyPlayer player) {
        executeInsideTransaction(entityManager -> entityManager.remove(player));
    }
}
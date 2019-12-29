package database;

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
        Query query = entityManager.createQuery("SELECT e FROM players e");
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
}
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
        Query query = entityManager.createQuery("SELECT e FROM FantasyPlayer e");
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
}
package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import athlete.RealTeam;

public class JpaRealTeamDao extends Dao<RealTeam> {
    @Override
    public Optional<RealTeam> get(long id) {
        return Optional.ofNullable(entityManager.find(RealTeam.class, id));
    }

    @Override
    public List<RealTeam> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM real_teams e");
        return query.getResultList();
    }

    @Override
    public void save(RealTeam realTeam) {
        executeInsideTransaction(entityManager -> entityManager.persist(realTeam));
    }

    @Override
    public void update(RealTeam realTeam) {
        executeInsideTransaction(entityManager -> entityManager.merge(realTeam));
    }

    @Override
    public void delete(RealTeam realTeam) {
        executeInsideTransaction(entityManager -> entityManager.remove(realTeam));
    }
}
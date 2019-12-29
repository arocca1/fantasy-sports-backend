package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import fantasyuser.Team;

public class JpaTeamDao extends Dao<Team> {
    @Override
    public Optional<Team> get(long id) {
        return Optional.ofNullable(entityManager.find(Team.class, id));
    }

    @Override
    public List<Team> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Team e");
        return query.getResultList();
    }

    @Override
    public void save(Team team) {
        executeInsideTransaction(entityManager -> entityManager.persist(team));
    }

    @Override
    public void update(Team team) {
        executeInsideTransaction(entityManager -> entityManager.merge(team));
    }

    @Override
    public void delete(Team team) {
        executeInsideTransaction(entityManager -> entityManager.remove(team));
    }
}
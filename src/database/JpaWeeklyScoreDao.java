package database;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import fantasyuser.WeeklyScore;

public class JpaWeeklyScoreDao extends Dao<WeeklyScore> {
    @Override
    public Optional<WeeklyScore> get(long id) {
        return Optional.ofNullable(entityManager.find(WeeklyScore.class, id));
    }

    @Override
    public List<WeeklyScore> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM WeeklyScore e");
        return query.getResultList();
    }

    @Override
    public void save(WeeklyScore weeklyScore) {
        executeInsideTransaction(entityManager -> entityManager.persist(weeklyScore));
    }

    @Override
    public void update(WeeklyScore weeklyScore) {
        executeInsideTransaction(entityManager -> entityManager.merge(weeklyScore));
    }

    @Override
    public void delete(WeeklyScore weeklyScore) {
        executeInsideTransaction(entityManager -> entityManager.remove(weeklyScore));
    }
}
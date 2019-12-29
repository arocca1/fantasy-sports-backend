package database;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public abstract class Dao<T> {
	protected EntityManager entityManager;

	public Optional<T> get(long id) {
		return Optional.empty();
	}
	public List<T> getAll() {
		return new ArrayList<T>();
	}
    public  void save(T t) { }
    public void update(T t) { }
    public void delete(T t) { }

    protected void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}

package database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import fantasyuser.User;

public class JpaUserDaoTest {
	@Test
	void testEverything() {
		User u = new User("I am bad at this", "Antonio Rocca");
		JpaUserDao userDao = new JpaUserDao();
		userDao.save(u);
		List<User> usersInDb = userDao.getAll();
		assertEquals(1, usersInDb.size());
		assertEquals(usersInDb.get(0).getName(), u.getName());
		// change user to the db one
		u = usersInDb.get(0);
		userDao.delete(u);
		// should now be empty
		usersInDb = userDao.getAll();
		assertEquals(0, usersInDb.size());
	}
}

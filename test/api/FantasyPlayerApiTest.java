package api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FantasyPlayerApiTest {
	@Test
	void testProjectedAndActualScoreReturnNothingButQuery() {
		assertEquals(0.0, FantasyPlayerApi.getProjectedScore(1, 1, 1));
		assertEquals(0.0, FantasyPlayerApi.getActualScore(1, 1, 1));
	}
}

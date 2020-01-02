package api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PlayerScoreApiTest {
	@Test
	void testProjectedAndActualScoreReturnNothingButQuery() {
		assertEquals(0.0, PlayerScoringApi.getProjectedScore(1, 1, 1, 1));
		assertEquals(0.0, PlayerScoringApi.getActualScore(1, 1, 1, 1));
	}

	@Test
	void testAllScoresReturnNothingButDoesQuery() {
		assertEquals(0, PlayerScoringApi.getSeasonScores(1, 1, 1).size());
	}
}

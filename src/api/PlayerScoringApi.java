package api;

import java.util.List;

import database.JpaWeeklyScoreDao;
import fantasyuser.WeeklyScore;

public class PlayerScoringApi {
	private static final JpaWeeklyScoreDao weeklyScoreDao = new JpaWeeklyScoreDao();

	public static double getProjectedScore(long leagueId, int week, long playerId) {
		return weeklyScoreDao.getProjectedScore(leagueId, week, playerId).orElse(0.0);
	}

	public static double getActualScore(long leagueId, int week, long playerId) {
		return weeklyScoreDao.getActualScore(leagueId, week, playerId).orElse(0.0);
	}

	public static List<WeeklyScore> getSeasonScores(long leagueId, long playerId) {
		return weeklyScoreDao.getAllForSeasonAndPlayer(leagueId, playerId);
	}
}

package api;

import java.util.List;

import database.JpaWeeklyScoreDao;
import fantasyuser.WeeklyScore;

public class PlayerScoringApi {
	private static final JpaWeeklyScoreDao weeklyScoreDao = new JpaWeeklyScoreDao();

	public static double getProjectedScore(long seasonId, int week, long playerId) {
		return weeklyScoreDao.getProjectedScore(seasonId, week, playerId).orElse(0.0);
	}

	public static double getProjectedScore(long leagueId, int seasonYear, int week, long playerId) {
		return weeklyScoreDao.getProjectedScore(leagueId, seasonYear, week, playerId).orElse(0.0);
	}

	public static double getActualScore(long seasonId, int week, long playerId) {
		return weeklyScoreDao.getActualScore(seasonId, week, playerId).orElse(0.0);
	}

	public static double getActualScore(long leagueId, int seasonYear, int week, long playerId) {
		return weeklyScoreDao.getActualScore(leagueId, seasonYear, week, playerId).orElse(0.0);
	}

	public static List<WeeklyScore> getSeasonScores(long seasonId, long playerId) {
		return weeklyScoreDao.getAllForSeasonAndPlayer(seasonId, playerId);
	}

	public static List<WeeklyScore> getSeasonScores(long leagueId, int seasonYear, long playerId) {
		return weeklyScoreDao.getAllForSeasonAndPlayer(leagueId, seasonYear, playerId);
	}
}

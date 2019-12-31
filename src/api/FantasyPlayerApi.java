package api;

import database.JpaWeeklyScoreDao;

public class FantasyPlayerApi {
	public static double getProjectedScore(long leagueId, int week, long fantasyPlayerId) {
		JpaWeeklyScoreDao weeklyScoreDao = new JpaWeeklyScoreDao();
		return weeklyScoreDao.getProjectedScore(leagueId, week, fantasyPlayerId).orElse(0.0);
	}

	public static double getActualScore(long leagueId, int week, long fantasyPlayerId) {
		JpaWeeklyScoreDao weeklyScoreDao = new JpaWeeklyScoreDao();
		return weeklyScoreDao.getActualScore(leagueId, week, fantasyPlayerId).orElse(0.0);
	}
}

package api;

import database.JpaWeeklyScoreDao;

public class TeamScoringApi {
	private static final JpaWeeklyScoreDao weeklyScoreDao = new JpaWeeklyScoreDao();

	public static double getWeekProjectedScore(long teamId, long weekId) {
		return weeklyScoreDao.getTeamProjectedScore(teamId, weekId).orElse(0.0);
	}

	public static double getWeekProjectedScore(long teamId, long seasonId, int week) {
		return weeklyScoreDao.getTeamProjectedScore(teamId, seasonId, week).orElse(0.0);
	}

	public static double getWeekProjectedScore(long teamId, long leagueId, int seasonYear, int week) {
		return weeklyScoreDao.getTeamProjectedScore(teamId, leagueId, seasonYear, week).orElse(0.0);
	}

	public static double getCurrentWeekProjectedScore(long teamId, long seasonId) {
		return weeklyScoreDao.getTeamCurrentWeekProjectedScore(teamId, seasonId).orElse(0.0);
	}

	public static double getWeekActualScore(long teamId, long weekId) {
		return weeklyScoreDao.getTeamActualScore(teamId, weekId).orElse(0.0);
	}

	public static double getWeekActualScore(long teamId, long seasonId, int week) {
		return weeklyScoreDao.getTeamActualScore(teamId, seasonId, week).orElse(0.0);
	}

	public static double getWeekActualScore(long teamId, long leagueId, int seasonYear, int week) {
		return weeklyScoreDao.getTeamActualScore(teamId, leagueId, seasonYear, week).orElse(0.0);
	}

	public static double getCurrentWeekActualScore(long teamId, long seasonId) {
		return weeklyScoreDao.getTeamCurrentWeekActualScore(teamId, seasonId).orElse(0.0);
	}
}

package api;

import java.util.List;

import athlete.Player;
import database.JpaPlayerDao;

public class TeamApi {
	private static JpaPlayerDao playerDao = new JpaPlayerDao();

	public static List<Player> getStarters(long teamId, long weekId) {
		return playerDao.getStarters(teamId, weekId);
	}

	public static List<Player> getStarters(long teamId, long seasonId, int week) {
		return playerDao.getStarters(teamId, seasonId, week);
	}

	public static List<Player> getFullTeam(long teamId, long weekId) {
		return playerDao.getFullTeam(teamId, weekId);
	}

	public static List<Player> getFullTeam(long teamId, long seasonId, int week) {
		return playerDao.getFullTeam(teamId, seasonId, week);
	}

	public static List<Player> getCurrentFullTeam(long teamId, long seasonId) {
		return playerDao.getCurrentFullTeam(teamId, seasonId);
	}
/*

	

	public static boolean setAsStarter(long teamId, long playerId) {
		// check that player is actually on team in first place
		// make sure that the team's position requirements are met
	}*/
}

package api;

import java.util.List;
import java.util.Optional;

import athlete.Player;
import database.JpaPlayerDao;
import database.JpaPlayerTeamRecordDao;
import database.JpaSeasonLineupPositionRequirementDao;
import database.JpaWeekDao;
import fantasyuser.PlayerTeamRecord;
import fantasyuser.SeasonLineupPositionRequirement;
import fantasyuser.Week;

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

	public static boolean setAsStarter(long teamId, long seasonId, long playerId) {
		// check that player actually exists
		Optional<Player> playerOptional = playerDao.get(playerId);
		if (!playerOptional.isEmpty()) {
			Player player = playerOptional.get();
			JpaWeekDao weekDao = new JpaWeekDao();
			Optional<Week> currentWeekOptional = weekDao.getCurrentWeek(seasonId);
			if (!currentWeekOptional.isEmpty()) {
				// check that player is actually on team in first place
				JpaPlayerTeamRecordDao playerTeamRecordDao = new JpaPlayerTeamRecordDao();
				Week currentWeek = currentWeekOptional.get();
				Optional<PlayerTeamRecord> playerRecordOptional = playerTeamRecordDao.get(playerId, teamId, currentWeek.getId());
				if (!playerRecordOptional.isEmpty()) {
					PlayerTeamRecord playerRecord = playerRecordOptional.get();
					JpaSeasonLineupPositionRequirementDao lineupRequirementDao = new JpaSeasonLineupPositionRequirementDao();
					long positionId = player.getPosition().getId();
					Optional<SeasonLineupPositionRequirement> lineupRequirementOptional = lineupRequirementDao.getForSeasonAndPosition(seasonId, positionId);
					if (!lineupRequirementOptional.isEmpty()) {
						// make sure that the team's position requirements are not exceeded by setting this player to starter
						SeasonLineupPositionRequirement lineupRequirement = lineupRequirementOptional.get();
						int numStartersInPosition = playerTeamRecordDao.numStartersInPosition(teamId, currentWeek.getId(), positionId);
						if (numStartersInPosition < lineupRequirement.getNum()) {
							playerRecord.makeStarter();
							playerTeamRecordDao.update(playerRecord);
							return true;
						} else {
							throw new RuntimeException("There are too many starters at this position. You need to move someone to the bench");
						}
					} else {
						throw new RuntimeException("No lineup requirements for this kind of player, i.e. you're not allowed to start them");
					}
				} else {
					throw new RuntimeException("The player is not on the current team");
				}
			} else {
				throw new RuntimeException("There is no current week in this league's season");
			}
		} else {
			throw new RuntimeException("This player does not exist");
		}
	}

	public static boolean setAsStarter(long teamId, long seasonId, long playerId, long playerToReplaceId) {
		// check that player actually exists
		Optional<Player> playerOptional = playerDao.get(playerId);
		if (!playerOptional.isEmpty()) {
			Player player = playerOptional.get();
			Optional<Player> playerToReplaceOptional = playerDao.get(playerToReplaceId);
			if (!playerToReplaceOptional.isEmpty()) {
				Player playerToReplace = playerToReplaceOptional.get();
				if (player.getPosition().getId() == playerToReplace.getPosition().getId()) {
					JpaWeekDao weekDao = new JpaWeekDao();
					Optional<Week> currentWeekOptional = weekDao.getCurrentWeek(seasonId);
					if (!currentWeekOptional.isEmpty()) {
						// check that player is actually on team
						JpaPlayerTeamRecordDao playerTeamRecordDao = new JpaPlayerTeamRecordDao();
						Week currentWeek = currentWeekOptional.get();
						Optional<PlayerTeamRecord> playerTeamRecordOptional = playerTeamRecordDao.get(playerId, teamId, currentWeek.getId());
						if (!playerTeamRecordOptional.isEmpty()) {
							PlayerTeamRecord playerTeamRecord = playerTeamRecordOptional.get();
							// check that player to replace is on team as well
							Optional<PlayerTeamRecord> playerToReplaceTeamRecordOptional = playerTeamRecordDao.get(playerToReplaceId, teamId, currentWeek.getId());
							if (!playerToReplaceTeamRecordOptional.isEmpty()) {
								PlayerTeamRecord playerToReplaceTeamRecord = playerToReplaceTeamRecordOptional.get();
								JpaSeasonLineupPositionRequirementDao lineupRequirementDao = new JpaSeasonLineupPositionRequirementDao();
								long positionId = player.getPosition().getId();
								Optional<SeasonLineupPositionRequirement> lineupRequirementOptional = lineupRequirementDao.getForSeasonAndPosition(seasonId, positionId);
								if (!lineupRequirementOptional.isEmpty()) {
									// make sure that the team's position requirements are not exceeded by setting this player to starter
									SeasonLineupPositionRequirement lineupRequirement = lineupRequirementOptional.get();
									int numStartersInPosition = playerTeamRecordDao.numStartersInPosition(teamId, currentWeek.getId(), positionId);
									if (numStartersInPosition < lineupRequirement.getNum()) {
										// move player to starting lineup
										playerTeamRecord.makeStarter();
										playerTeamRecordDao.update(playerTeamRecord);
										// move player to replace to bench
										playerToReplaceTeamRecord.moveToBench();
										playerTeamRecordDao.update(playerToReplaceTeamRecord);
										return true;
									} else {
										throw new RuntimeException("There are too many starters at this position. You need to move someone to the bench");
									}
								} else {
									throw new RuntimeException("No lineup requirements for this kind of player, i.e. you're not allowed to start them");
								}
							} else {
								throw new RuntimeException("The player to replace is not on the current team");
							}
						} else {
							throw new RuntimeException("The player is not on the current team");
						}
					} else {
						throw new RuntimeException("There is no current week in this league's season");
					}
				} else {
					throw new RuntimeException("The players do not play the same position");
				}
			} else {
				throw new RuntimeException("The player to replace does not exist");
			}
		} else {
			throw new RuntimeException("This player does not exist");
		}
	}
}

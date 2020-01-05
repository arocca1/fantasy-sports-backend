package api;

import java.util.List;
import java.util.Optional;

import athlete.Player;
import database.JpaPlayerDao;
import database.JpaPlayerTeamRecordDao;
import database.JpaSeasonDao;
import database.JpaSeasonLineupPositionRequirementDao;
import database.JpaTeamDao;
import database.JpaWeekDao;
import database.JpaWeeklyScoreDao;
import fantasyuser.PlayerTeamRecord;
import fantasyuser.Season;
import fantasyuser.SeasonLineupPositionRequirement;
import fantasyuser.Team;
import fantasyuser.Week;
import fantasyuser.WeeklyScore;

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

	public static boolean addPlayer(long teamId, long seasonId, long playerId) {
		// check that player actually exists
		Optional<Player> playerOptional = playerDao.get(playerId);
		if (!playerOptional.isEmpty()) {
			Player player = playerOptional.get();
			JpaTeamDao teamDao = new JpaTeamDao();
			Optional<Team> teamOptional = teamDao.get(teamId);
			if (!teamOptional.isEmpty()) {
				Team team = teamOptional.get();
				JpaSeasonDao seasonDao = new JpaSeasonDao();
				Optional<Season> seasonOptional = seasonDao.get(seasonId);
				if (!seasonOptional.isEmpty()) {
					Season season = seasonOptional.get();
					JpaWeekDao weekDao = new JpaWeekDao();
					Optional<Week> currentWeekOptional = weekDao.getCurrentWeek(seasonId);
					if (!currentWeekOptional.isEmpty()) {
						Week currentWeek = currentWeekOptional.get();
						// check that player is not actually on team
						JpaPlayerTeamRecordDao playerTeamRecordDao = new JpaPlayerTeamRecordDao();
						Optional<PlayerTeamRecord> playerTeamRecordOptional = playerTeamRecordDao.get(playerId, teamId, currentWeek.getId());
						if (playerTeamRecordOptional.isEmpty()) {
							// make sure that the player is not on another team
							Optional<PlayerTeamRecord> playerAnyTeamRecordOptional = playerTeamRecordDao.get(playerId, currentWeek.getId());
							if (playerAnyTeamRecordOptional.isEmpty()) {
								if (teamDao.getNumPlayersOnTeam(teamId, currentWeek.getId()) < season.getMaxTeamSize()) {
									JpaWeeklyScoreDao weeklyScoreDao = new JpaWeeklyScoreDao();
									Optional<WeeklyScore> playerWeeklyScoreOptional = weeklyScoreDao.get(teamId, currentWeek.getId(), playerId);
									if (!playerWeeklyScoreOptional.isEmpty()) {
										PlayerTeamRecord playerTeamRecord = new PlayerTeamRecord(player, team, currentWeek);
										playerTeamRecordDao.save(playerTeamRecord);
										WeeklyScore playerWeeklyScore = playerWeeklyScoreOptional.get();
										playerWeeklyScore.setTeam(team);
										weeklyScoreDao.update(playerWeeklyScore);
										return true;
									} else {
										throw new RuntimeException("The player does not have a score associated with their current team. Something is weird");
									}
								} else {
									throw new RuntimeException("The team is already at maximum size. Drop a player or replace with a current player");
								}
							} else {
								throw new RuntimeException("This player is on another team");
							}
						} else {
							throw new RuntimeException("The player is already on the current team");
						}
					} else {
						throw new RuntimeException("There is no current week in this league's season");
					}
				} else {
					throw new RuntimeException("The specified season does not exist");
				}
			} else {
				throw new RuntimeException("The specified team does not exist");
			}
		} else {
			throw new RuntimeException("This player does not exist");
		}
	}

	public static boolean addPlayer(long teamId, long seasonId, long playerId, long playerToReplaceId) {
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
						// check that player is not actually on team
						JpaPlayerTeamRecordDao playerTeamRecordDao = new JpaPlayerTeamRecordDao();
						Week currentWeek = currentWeekOptional.get();
						Optional<PlayerTeamRecord> playerTeamRecordOptional = playerTeamRecordDao.get(playerId, teamId, currentWeek.getId());
						if (playerTeamRecordOptional.isEmpty()) {
							// make sure that the player is not on another team
							Optional<PlayerTeamRecord> playerAnyTeamRecordOptional = playerTeamRecordDao.get(playerId, currentWeek.getId());
							if (playerAnyTeamRecordOptional.isEmpty()) {
								// check if other player is on the current team
								Optional<PlayerTeamRecord> playerToReplaceTeamRecordOptional = playerTeamRecordDao.get(playerToReplaceId, teamId, currentWeek.getId());
								if (!playerToReplaceTeamRecordOptional.isEmpty()) {
									PlayerTeamRecord playerToReplaceTeamRecord = playerToReplaceTeamRecordOptional.get();
									JpaWeeklyScoreDao weeklyScoreDao = new JpaWeeklyScoreDao();
									Optional<WeeklyScore> playerWeeklyScoreOptional = weeklyScoreDao.get(teamId, currentWeek.getId(), playerId);
									if (!playerWeeklyScoreOptional.isEmpty()) {
										Optional<WeeklyScore> playerToReplaceWeeklyScoreOptional = weeklyScoreDao.get(teamId, currentWeek.getId(), playerToReplaceId);
										if (!playerToReplaceWeeklyScoreOptional.isEmpty()) {
											// going to assume that lineup requirements are met because we are replacing one player with another
											// will have to explicitly set is starter after and that will check lineup requirements
											// add player team record for player
											PlayerTeamRecord playerTeamRecord = new PlayerTeamRecord(player, playerToReplaceTeamRecord.getTeam(), currentWeek);
											playerTeamRecordDao.save(playerTeamRecord);
											WeeklyScore playerWeeklyScore = playerWeeklyScoreOptional.get();
											playerWeeklyScore.setTeam(playerToReplaceTeamRecord.getTeam());
											weeklyScoreDao.update(playerWeeklyScore);
											// delete player team record for player to replace
											playerTeamRecordDao.delete(playerToReplaceTeamRecord);
											// clear team id from weekly score for player to replace
											WeeklyScore playerToReplaceWeeklyScore = playerToReplaceWeeklyScoreOptional.get();
											playerToReplaceWeeklyScore.setTeam(null);
											weeklyScoreDao.update(playerToReplaceWeeklyScore);
											return true;
										} else {
											throw new RuntimeException("The player to replace does not have a score associated with their current team. Something is weird");
										}
									} else {
										throw new RuntimeException("The player does not have a score associated with their current team. Something is weird");
									}
								} else {
									throw new RuntimeException("The player to replace is not on the current team");
								}
							} else {
								throw new RuntimeException("This player is on another team");
							}
						} else {
							throw new RuntimeException("The player is already on the current team");
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

	public static boolean dropPlayer(long teamId, long seasonId, long playerId) {
		// check that player actually exists
		Optional<Player> playerOptional = playerDao.get(playerId);
		if (!playerOptional.isEmpty()) {
			Player player = playerOptional.get();
			JpaWeekDao weekDao = new JpaWeekDao();
			Optional<Week> currentWeekOptional = weekDao.getCurrentWeek(seasonId);
			if (!currentWeekOptional.isEmpty()) {
				// check that player is not actually on team
				JpaPlayerTeamRecordDao playerTeamRecordDao = new JpaPlayerTeamRecordDao();
				Week currentWeek = currentWeekOptional.get();
				Optional<PlayerTeamRecord> playerTeamRecordOptional = playerTeamRecordDao.get(playerId, teamId, currentWeek.getId());
				// make sure that the player is on the current team
				if (!playerTeamRecordOptional.isEmpty()) {
					JpaWeeklyScoreDao weeklyScoreDao = new JpaWeeklyScoreDao();
					Optional<WeeklyScore> playerWeeklyScoreOptional = weeklyScoreDao.get(teamId, currentWeek.getId(), playerId);
					if (!playerWeeklyScoreOptional.isEmpty()) {
						PlayerTeamRecord playerTeamRecord = playerTeamRecordOptional.get();
						playerTeamRecordDao.delete(playerTeamRecord);
						WeeklyScore playerWeeklyScore = playerWeeklyScoreOptional.get();
						playerWeeklyScore.setTeam(null);
						weeklyScoreDao.update(playerWeeklyScore);
						return true;
					} else {
						throw new RuntimeException("The player does not have a score associated with their current team. Something is weird");
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
}

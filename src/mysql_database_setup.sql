CREATE DATABASE fantasy_sports;

USE fantasy_sports;

DROP TABLE IF EXISTS weekly_scores;
DROP TABLE IF EXISTS player_team_records;
DROP TABLE IF EXISTS weeks;
DROP TABLE IF EXISTS seasons;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS matchups;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS divisions;
DROP TABLE IF EXISTS leagues;
DROP TABLE IF EXISTS real_teams;
DROP TABLE IF EXISTS positions;
DROP TABLE IF EXISTS real_leagues;
DROP TABLE IF EXISTS sports;

CREATE TABLE sports (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=INNODB;

CREATE TABLE real_leagues (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	sportId BIGINT NOT NULL,
	CONSTRAINT fk_real_leagues_sportId
	FOREIGN KEY (sportId)
		REFERENCES sports(id)
) ENGINE=INNODB;

CREATE TABLE positions (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	sportId BIGINT NOT NULL,
	CONSTRAINT fk_positions_sportId
	FOREIGN KEY (sportId)
		REFERENCES sports(id)
) ENGINE=INNODB;

CREATE TABLE real_teams (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	realLeagueId BIGINT NOT NULL,
	CONSTRAINT fk_real_teams_realLeagueId
	FOREIGN KEY (realLeagueId)
		REFERENCES real_leagues(id)
) ENGINE=INNODB;

CREATE TABLE leagues (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	sportId BIGINT NOT NULL,
	CONSTRAINT fk_leagues_sportId
	FOREIGN KEY (sportId)
		REFERENCES sports(id),
	realLeagueId BIGINT,
	CONSTRAINT fk_leagues_realLeagueId
	FOREIGN KEY (realLeagueId)
		REFERENCES real_leagues(id)
) ENGINE=INNODB;

CREATE TABLE divisions (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	leagueId BIGINT NOT NULL,
	CONSTRAINT fk_divisions_leagueId
	FOREIGN KEY (leagueId)
		REFERENCES leagues(id)
) ENGINE=INNODB;

CREATE TABLE users (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	realName VARCHAR(255),
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=INNODB;

CREATE TABLE teams (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	userId BIGINT,
	CONSTRAINT fk_teams_userId
	FOREIGN KEY (userId)
		REFERENCES users(id),
	divisionId BIGINT,
	CONSTRAINT fk_teams_divisionId
	FOREIGN KEY (divisionId)
		REFERENCES divisions(id),
	leagueId BIGINT NOT NULL,
	CONSTRAINT fk_teams_leagueId
	FOREIGN KEY (leagueId)
		REFERENCES leagues(id)
) ENGINE=INNODB;

CREATE TABLE matchups (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	matchupDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	homeTeamId BIGINT NOT NULL,
	CONSTRAINT fk_matchups_homeTeamId
	FOREIGN KEY (homeTeamId)
		REFERENCES teams(id),
	awayTeamId BIGINT NOT NULL,
	CONSTRAINT fk_matchups_awayTeamId
	FOREIGN KEY (awayTeamId)
		REFERENCES teams(id),
	winningTeamId BIGINT,
	CONSTRAINT fk_matchups_winningTeamId
	FOREIGN KEY (winningTeamId)
		REFERENCES teams(id),
	losingTeamId BIGINT,
	CONSTRAINT fk_matchups_losingTeamId
	FOREIGN KEY (losingTeamId)
		REFERENCES teams(id)
) ENGINE=INNODB;

CREATE TABLE players (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	positionId BIGINT NOT NULL,
	CONSTRAINT fk_players_positionId
	FOREIGN KEY (positionId)
		REFERENCES positions(id),
	realTeamId BIGINT NOT NULL,
	CONSTRAINT fk_players_realTeamId
	FOREIGN KEY (realTeamId)
		REFERENCES real_teams(id)
) ENGINE=INNODB;

CREATE TABLE seasons (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	year INT NOT NULL,
	numWeeks INT NOT NULL,
	scoringBreakdown TEXT NOT NULL,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	leagueId BIGINT NOT NULL,
	CONSTRAINT fk_seasons_leagueId
	FOREIGN KEY (leagueId)
		REFERENCES leagues(id)
) ENGINE=INNODB;

CREATE TABLE weeks (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	num INT NOT NULL,
	startDate TIMESTAMP NOT NULL,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	seasonId BIGINT NOT NULL,
	CONSTRAINT fk_weeks_seasonId
	FOREIGN KEY (seasonId)
		REFERENCES seasons(id)
) ENGINE=INNODB;

CREATE TABLE player_team_records (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	playerId BIGINT NOT NULL,
	CONSTRAINT fk_fantasy_players_playerId
	FOREIGN KEY (playerId)
		REFERENCES players(id),
	teamId BIGINT NOT NULL,
	CONSTRAINT fk_fantasy_players_teamId
	FOREIGN KEY (teamId)
		REFERENCES teams(id)
) ENGINE=INNODB;

CREATE TABLE weekly_scores (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	projectedScore DOUBLE NOT NULL DEFAULT 0.0,
	actualScore DOUBLE NOT NULL DEFAULT 0.0,
	scoringBreakdown TEXT NOT NULL,
	createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	weekId BIGINT NOT NULL,
	CONSTRAINT fk_weekly_scores_weekId
	FOREIGN KEY (weekId)
		REFERENCES weeks(id),
	playerId BIGINT NOT NULL,
	CONSTRAINT fk_weekly_scores_playerId
	FOREIGN KEY (playerId)
		REFERENCES players(id),
	teamId BIGINT,
	CONSTRAINT fk_weekly_scores_teamId
	FOREIGN KEY (teamId)
		REFERENCES teams(id)
) ENGINE=INNODB;
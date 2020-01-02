package api;

import java.util.Optional;

import database.JpaSeasonDao;
import fantasyuser.ScoreBreakdown;
import fantasyuser.Season;

public class SeasonScoringApi {
	private static final JpaSeasonDao seasonDao = new JpaSeasonDao();

	private static boolean doUpdateSeasonScoringBreakdown(Optional<Season> seasonOptional, ScoreBreakdown breakdown) {
		if (seasonOptional.isPresent()) {
			Season season = seasonOptional.get();
			season.setScoringBreakdown(breakdown);
			seasonDao.update(season);
			return true;
		} else {
			return false;
		}
	}

	public static boolean updateSeasonScoreBreakdown(long seasonId, ScoreBreakdown breakdown) {
		return doUpdateSeasonScoringBreakdown(seasonDao.get(seasonId), breakdown);
	}

	public static boolean updateSeasonScoreBreakdown(long leagueId, int seasonYear, ScoreBreakdown breakdown) {
		return doUpdateSeasonScoringBreakdown(seasonDao.get(leagueId, seasonYear), breakdown);
	}

	private static boolean doUpdateSeasonScoringBreakdown(Optional<Season> seasonOptional, String playerType, String stat, double scoreValue) {
		if (seasonOptional.isPresent()) {
			Season season = seasonOptional.get();
			ScoreBreakdown breakdown = season.getScoringBreakdown();
			breakdown.updateStatScore(playerType, stat, scoreValue);
			seasonDao.update(season);
			return true;
		} else {
			return false;
		}
	}

	public static boolean updateSeasonScoringAttribute(long seasonId, String playerType, String stat, double scoreValue) {
		return doUpdateSeasonScoringBreakdown(seasonDao.get(seasonId), playerType, stat, scoreValue);
	}

	public static boolean updateSeasonScoringAttribute(long leagueId, int seasonYear, String playerType, String stat, double scoreValue) {
		return doUpdateSeasonScoringBreakdown(seasonDao.get(leagueId, seasonYear), playerType, stat, scoreValue);
	}
}

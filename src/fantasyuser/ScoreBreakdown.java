package fantasyuser;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ScoreBreakdown implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, Map<String, Double>> playerTypeToStatToScore;

	// TODO probably put this in the database in some form. This class should be a future database table
	private static final Map<String, Map<String, Double>> NFL_SCORING;
	static {
		NFL_SCORING = new HashMap<String, Map<String, Double>>();
		// offensive player scoring, like QB, TE, RB, WR
		Map<String, Double> offensivePlayerScoring = new HashMap<String, Double>();
		offensivePlayerScoring.put("throwing_td", 4.0);
		offensivePlayerScoring.put("throwing_yard", 0.025);
		offensivePlayerScoring.put("interception", -2.0);
		offensivePlayerScoring.put("receiving_td", 6.0);
		offensivePlayerScoring.put("receiving_yard", 0.1);
		offensivePlayerScoring.put("reception", 1.0);
		offensivePlayerScoring.put("rushing_yard", 0.1);
		offensivePlayerScoring.put("return_yard", 0.001);
		offensivePlayerScoring.put("fumble", -4.0);
		// defensive player scoring, like CB, LB, DL
		Map<String, Double> defensivePlayerScoring = new HashMap<String, Double>();
		defensivePlayerScoring.put("interception", 2.0);
		defensivePlayerScoring.put("fumble", 2.0);
		defensivePlayerScoring.put("sack", 2.0);
		defensivePlayerScoring.put("pass_defensed", 0.5);
		defensivePlayerScoring.put("tackle", 0.01);
		defensivePlayerScoring.put("touchdown", 6.0);
		// defensive team scoring for full defense
		Map<String, Double> defensiveScoring = new HashMap<String, Double>();
		defensiveScoring.put("interception", 2.0);
		defensiveScoring.put("fumble", 2.0);
		defensiveScoring.put("sack", 2.0);
		defensiveScoring.put("touchdown", 6.0);
	}
	public static final ScoreBreakdown NFL_SCORING_SINGLETON = new ScoreBreakdown(NFL_SCORING);

	public ScoreBreakdown(Map<String, Map<String, Double>> playerTypeToStatToScore) {
		this.playerTypeToStatToScore = playerTypeToStatToScore;
	}

	public double calculateScore(ScoreBreakdown breakdown) {
		Map<String, Map<String, Double>> fullScoreBreakdown = breakdown.getPlayerTypeToStatToScore();
		double fullScore = 0;
		for (Map.Entry<String, Map<String, Double>> e : fullScoreBreakdown.entrySet()) {
			String playerType = e.getKey();
			Map<String, Double> scorePerPlay = e.getValue();
			if (playerTypeToStatToScore.containsKey(playerType)) {
				Map<String, Double> leagueScoringPerPlayerType = playerTypeToStatToScore.get(playerType);
				for (Map.Entry<String, Double> f : scorePerPlay.entrySet()) {
					String playType = f.getKey();
					Double numPlays = f.getValue();
					fullScore += leagueScoringPerPlayerType.getOrDefault(playType, 0.0) * numPlays;
				}
			}
		}
		return fullScore;
	}

	public Map<String, Map<String, Double>> getPlayerTypeToStatToScore() {
		return playerTypeToStatToScore;
	}

	public void updateStatScore(String playerType, String stat, double scoreValue) {
		Map<String, Double> individualPlayerTypeScoring = playerTypeToStatToScore.getOrDefault(playerType, new HashMap<String, Double>());
		individualPlayerTypeScoring.put(stat, scoreValue);
		playerTypeToStatToScore.put(playerType, individualPlayerTypeScoring);
	}
}

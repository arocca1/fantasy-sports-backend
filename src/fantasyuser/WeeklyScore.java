package fantasyuser;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import database.ScoreBreakdownToStringConverter;

@Entity
@Table( name = "WEEKLY_SCORES" )
public class WeeklyScore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double projectedScore;
	private double actualScore;
	@Convert(converter = ScoreBreakdownToStringConverter.class)
	// This is better served as a separate column, but JSON is better currently to handle all sports
	private ScoreBreakdown scoringBreakdown;
	private Date createdAt = Calendar.getInstance().getTime();
	private long weekId;
	private long playerId;

	public WeeklyScore() { }

	public WeeklyScore(double projectedScore, double actualScore, ScoreBreakdown scoringBreakdown, long weekId,
			long playerId) {
		this.projectedScore = projectedScore;
		this.actualScore = actualScore;
		this.scoringBreakdown = scoringBreakdown;
		this.weekId = weekId;
		this.playerId = playerId;
	}
}

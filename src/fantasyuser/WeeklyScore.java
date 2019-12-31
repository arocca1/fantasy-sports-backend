package fantasyuser;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import database.ScoreBreakdownToStringConverter;

@Entity
@Table( name = "WEEKLY_SCORES" )
public class WeeklyScore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double projectedScore = 0.0;
	private double actualScore = 0.0;
	@Convert(converter = ScoreBreakdownToStringConverter.class)
	// This is better served as a separate column, but JSON is better currently to handle all sports
	private ScoreBreakdown scoringBreakdown;
	private Date createdAt = Calendar.getInstance().getTime();
	@ManyToOne
    @JoinColumn(name="weekId", nullable=false)
	private Week week;
	@ManyToOne
    @JoinColumn(name="fantasyPlayerId", nullable=false)
	private FantasyPlayer fantasyPlayer;

	public WeeklyScore() { }

	public WeeklyScore(double projectedScore, double actualScore, ScoreBreakdown scoringBreakdown, Week week,
			FantasyPlayer fantasyPlayer) {
		this.projectedScore = projectedScore;
		this.actualScore = actualScore;
		this.scoringBreakdown = scoringBreakdown;
		this.week = week;
		this.fantasyPlayer = fantasyPlayer;
	}

	public WeeklyScore(double projectedScore, double actualScore, Week week, FantasyPlayer fantasyPlayer) {
		this(projectedScore, actualScore, null, week, fantasyPlayer);
	}

	public WeeklyScore(ScoreBreakdown scoringBreakdown, Week week, FantasyPlayer fantasyPlayer) {
		this(0.0, 0.0, scoringBreakdown, week, fantasyPlayer);
	}

	public WeeklyScore(Week week, FantasyPlayer fantasyPlayer) {
		this(0.0, 0.0, null, week, fantasyPlayer);
	}

	public double getProjectedScore() {
		return projectedScore;
	}

	public void setProjectedScore(double projectedScore) {
		this.projectedScore = projectedScore;
	}

	public double getActualScore() {
		return actualScore;
	}

	public void setActualScore(double actualScore) {
		this.actualScore = actualScore;
	}

	public ScoreBreakdown getScoringBreakdown() {
		return scoringBreakdown;
	}

	public void setScoringBreakdown(ScoreBreakdown scoringBreakdown) {
		this.scoringBreakdown = scoringBreakdown;
	}

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Week getWeek() {
		return week;
	}

	public FantasyPlayer getPlayer() {
		return fantasyPlayer;
	}
}

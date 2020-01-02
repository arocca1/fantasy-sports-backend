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
@Table( name = "SEASONS" )
public class Season {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int year;
	private int numWeeks;
	@Convert(converter = ScoreBreakdownToStringConverter.class)
	// This is better served as a separate column, but JSON is better currently to handle all sports
	private ScoreBreakdown scoringBreakdown;
	private Date createdAt = Calendar.getInstance().getTime();
	@ManyToOne
    @JoinColumn(name="leagueId", nullable=false)
	private League league;

	public Season() { }

	public Season(int year, int numWeeks, ScoreBreakdown scoringBreakdown, League league) {
		this.year = year;
		this.numWeeks = numWeeks;
		this.scoringBreakdown = scoringBreakdown;
		this.league = league;
	}

	public int getNumWeeks() {
		return numWeeks;
	}

	public void setNumWeeks(int numWeeks) {
		this.numWeeks = numWeeks;
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

	public int getYear() {
		return year;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public League getLeague() {
		return league;
	}
}

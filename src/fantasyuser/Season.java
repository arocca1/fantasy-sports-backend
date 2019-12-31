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
	private League league;

	public Season() { }

	public Season(int year, int numWeeks, ScoreBreakdown scoringBreakdown, League league) {
		this.year = year;
		this.numWeeks = numWeeks;
		this.scoringBreakdown = scoringBreakdown;
		this.league = league;
	}
}

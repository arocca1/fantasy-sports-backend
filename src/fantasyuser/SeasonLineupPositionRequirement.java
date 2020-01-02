package fantasyuser;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import athlete.Position;

// Currently does not support the idea of a flex position
@Entity
@Table( name = "SEASON_LINEUP_POSITION_REQUIREMENTS" )
public class SeasonLineupPositionRequirement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Date createdAt = Calendar.getInstance().getTime();
	@ManyToOne
    @JoinColumn(name="seasonId", nullable=false)
	private Season season;
	@ManyToOne
    @JoinColumn(name="positionId", nullable=false)
	private Position position;
	@ManyToOne
    @JoinColumn(name="weekId", nullable=false)
	private Week week;

	public SeasonLineupPositionRequirement(Season season, Position position, Week week) {
		super();
		this.season = season;
		this.position = position;
		this.week = week;
	}

	public Week getWeek() {
		return week;
	}

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Season getSeason() {
		return season;
	}

	public Position getPosition() {
		return position;
	}
}

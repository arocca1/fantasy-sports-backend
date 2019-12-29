package fantasyuser;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import athlete.RealLeague;

@Entity
@Table( name = "leagues" )
public class League {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private Date createdAt = Calendar.getInstance().getTime();
	private long sportId;
	private long realLeagueId;

	public League() { }

	public League(String name, long sportId, long realLeagueId) {
		this.name = name;
		this.sportId = sportId;
		this.realLeagueId = realLeagueId;
	}

	// Imagine doing a soccer fantasy league that we don't want to limit by individual real league
	public League(String name, long sportId) {
		this(name, sportId, RealLeague.NO_INDIVIDUAL_LEAGUE);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSportId() {
		return sportId;
	}

	public void setSportId(long sportId) {
		this.sportId = sportId;
	}

	public long getRealLeagueId() {
		return realLeagueId;
	}

	public void setRealLeagueId(long realLeagueId) {
		this.realLeagueId = realLeagueId;
	}

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

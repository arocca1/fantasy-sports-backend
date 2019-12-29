package fantasyuser;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "TEAMS" )
public class Team {
	public static long NO_TEAM_ID = -1;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	// TODO add season
	private Date createdAt = Calendar.getInstance().getTime();
	private long userId;
	private long divisionId;
	private long leagueId;

	public Team() { }

	public Team(String name, long divisionId, long leagueId) {
		this.name = name;
		this.divisionId = divisionId;
		this.leagueId = leagueId;
	}

	public Team(String name, long leagueId) {
		this(name, Division.NO_DIVISION_ID, leagueId);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(long divisionId) {
		this.divisionId = divisionId;
	}

	public long getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(long leagueId) {
		this.leagueId = leagueId;
	}

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

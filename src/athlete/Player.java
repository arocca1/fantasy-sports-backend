package athlete;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import fantasyuser.Team;

@Entity
@Table( name = "PLAYERS" )
public class Player {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	// TODO add retired / active
	private Date createdAt;
	private long realTeamId;
	private long teamId;

	public Player() { }

	public Player(String name, long realTeamId, long teamId) {
		this.name = name;
		this.realTeamId = realTeamId;
		this.teamId = teamId;
	}

	public Player(String name, long realTeamId) {
		this(name, realTeamId, Team.NO_TEAM_ID);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getRealTeamId() {
		return realTeamId;
	}

	public void setRealTeamId(long realTeamId) {
		this.realTeamId = realTeamId;
	}

	public long getTeamId() {
		return teamId;
	}

	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

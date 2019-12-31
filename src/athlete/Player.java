package athlete;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import fantasyuser.Team;

@Entity
@Table( name = "PLAYERS" )
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	// TODO add retired / active
	private Date createdAt = Calendar.getInstance().getTime();
	private RealTeam realTeam;
	private Team team;

	public Player() { }

	public Player(String name, RealTeam realTeam, Team team) {
		this.name = name;
		this.realTeam = realTeam;
		this.team = team;
	}

	public Player(String name, RealTeam realTeam) {
		this(name, realTeam, null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RealTeam getRealTeam() {
		return realTeam;
	}

	public void setRealTeam(RealTeam realTeam) {
		this.realTeam = realTeam;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

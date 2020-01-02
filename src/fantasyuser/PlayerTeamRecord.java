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

import athlete.Player;

@Entity
@Table( name = "PLAYER_TEAM_RECORDS" )
public class PlayerTeamRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private boolean isStarter = false;
	private Date createdAt = Calendar.getInstance().getTime();
	@ManyToOne
	@JoinColumn(name = "playerId", nullable = false)
	private Player player;
	@ManyToOne
	@JoinColumn(name = "teamId", nullable = false)
	private Team team;
	@ManyToOne
	@JoinColumn(name = "weekId", nullable = false)
	private Week week;

	public PlayerTeamRecord(boolean isStarter, Player player, Team team, Week week) {
		this.isStarter = isStarter;
		this.player = player;
		this.team = team;
		this.week = week;
	}

	public PlayerTeamRecord(Player player, Team team, Week week) {
		this(false, player, team, week);
	}

	public boolean isStarter() {
		return isStarter;
	}

	public void setStarter(boolean isStarter) {
		this.isStarter = isStarter;
	}

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Player getPlayer() {
		return player;
	}

	public Team getTeam() {
		return team;
	}

	public Week getWeek() {
		return week;
	}
}

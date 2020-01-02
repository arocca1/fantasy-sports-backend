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
	private Date createdAt = Calendar.getInstance().getTime();
	@ManyToOne
	@JoinColumn(name = "playerId", nullable = false)
	private Player player;
	@ManyToOne
	@JoinColumn(name = "teamId", nullable = false)
	private Team team;

	public PlayerTeamRecord(Player player, Team team) {
		this.player = player;
		this.team = team;
	}

	public PlayerTeamRecord(Player player) {
		this(player, null);
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

	public Player getPlayer() {
		return player;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

package fantasyuser;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table( name = "MATCHUPS" )
public class Matchup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Date matchupDate;
	private Date createdAt = Calendar.getInstance().getTime();
	@OneToOne(targetEntity = Team.class)
    @JoinColumn(name="homeTeamId", nullable=false)
	private Team homeTeam;
	@OneToOne(targetEntity = Team.class)
    @JoinColumn(name="awayTeamId", nullable=false)
	private Team awayTeam;
	@OneToOne(targetEntity = Team.class)
    @JoinColumn(name="winningTeamId", nullable=true)
	private Team winningTeam;
	@OneToOne(targetEntity = Team.class)
    @JoinColumn(name="losingTeamId", nullable=true)
	private Team losingTeam;

	public Matchup() { }

	public Matchup(Date matchupDate, Team homeTeam, Team awayTeam) {
		this.matchupDate = matchupDate;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}

	public Date getMatchupDate() {
		return matchupDate;
	}

	public void setMatchupDate(Date matchupDate) {
		this.matchupDate = matchupDate;
	}

	public Team getWinningTeam() {
		return winningTeam;
	}

	public void setWinningTeam(Team winningTeam) {
		this.winningTeam = winningTeam;
	}

	public Team getLosingTeam() {
		return losingTeam;
	}

	public void setLosingTeam(Team losingTeam) {
		this.losingTeam = losingTeam;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

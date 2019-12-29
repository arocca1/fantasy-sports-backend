package fantasyuser;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "MATCHUPS" )
public class Matchup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Date matchupDate;
	private Date createdAt = Calendar.getInstance().getTime();
	private long homeTeamId;
	private long awayTeamId;
	private long winningTeamId;
	private long losingTeamId;

	public Matchup() { }

	public Matchup(Date matchupDate, long homeTeamId, long awayTeamId) {
		this.matchupDate = matchupDate;
		this.homeTeamId = homeTeamId;
		this.awayTeamId = awayTeamId;
	}

	public Date getMatchupDate() {
		return matchupDate;
	}

	public void setMatchupDate(Date matchupDate) {
		this.matchupDate = matchupDate;
	}

	public long getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(long homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public long getAwayTeamId() {
		return awayTeamId;
	}

	public void setAwayTeamId(long awayTeamId) {
		this.awayTeamId = awayTeamId;
	}

	public long getWinningTeamId() {
		return winningTeamId;
	}

	public void setWinningTeamId(long winningTeamId) {
		this.winningTeamId = winningTeamId;
	}

	public long getLosingTeamId() {
		return losingTeamId;
	}

	public void setLosingTeamId(long losingTeamId) {
		this.losingTeamId = losingTeamId;
	}

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

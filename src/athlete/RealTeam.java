package athlete;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "REAL_TEAMS" )
public class RealTeam {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private Date createdAt;
	private long realLeagueId;

	public RealTeam() { }

	public RealTeam(String name, long realLeagueId) {
		this.name = name;
		this.realLeagueId = realLeagueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

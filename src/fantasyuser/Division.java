package fantasyuser;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "DIVISIONS" )
public class Division {
	public static long NO_DIVISION_ID = -1;
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private Date createdAt;
	private long leagueId;

	public Division() { }

	public Division(String name, long leagueId) {
		this.name = name;
		this.leagueId = leagueId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

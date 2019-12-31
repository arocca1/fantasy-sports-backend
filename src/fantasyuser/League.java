package fantasyuser;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import athlete.RealLeague;
import athlete.Sport;

@Entity
@Table( name = "leagues" )
public class League {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private Date createdAt = Calendar.getInstance().getTime();
	private Sport sport;
	private RealLeague realLeague;

	public League() { }

	public League(String name, Sport sport, RealLeague realLeague) {
		this.name = name;
		this.sport = sport;
		this.realLeague = realLeague;
	}

	// Imagine doing a soccer fantasy league that we don't want to limit by individual real league
	public League(String name, Sport sport) {
		this(name, sport, null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sport getSport() {
		return sport;
	}

	public RealLeague getRealLeague() {
		return realLeague;
	}

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

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

@Entity
@Table( name = "TEAMS" )
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private Date createdAt = Calendar.getInstance().getTime();
	@ManyToOne
    @JoinColumn(name="userId", nullable=false)
	private User user;
	@ManyToOne
    @JoinColumn(name="divisionId", nullable=false)
	private Division division;
	// TODO add season
	@ManyToOne
    @JoinColumn(name="leagueId", nullable=false)
	private League league;

	public Team() { }

	public Team(String name, User user, Division division, League league) {
		this.name = name;
		this.division = division;
		this.league = league;
	}

	public Team(String name, User user, League league) {
		this(name, user, null, league);
	}

	public Team(User user, League league) {
		this(user.getName(), user, league);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public League getLeague() {
		return league;
	}

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

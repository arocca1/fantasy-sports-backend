package athlete;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "REAL_TEAMS" )
public class RealTeam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private Date createdAt = Calendar.getInstance().getTime();
	private RealLeague realLeague;

	public RealTeam() { }

	public RealTeam(String name, RealLeague realLeague) {
		this.name = name;
		this.realLeague = realLeague;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

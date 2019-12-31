package athlete;

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
@Table( name = "REAL_LEAGUES" )
public class RealLeague {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private Date createdAt = Calendar.getInstance().getTime();
	@ManyToOne
	@JoinColumn(name = "sportId", nullable = false)
	private Sport sport;

	public RealLeague() { }

	public RealLeague(String name, Sport sport) {
		this.name = name;
		this.sport = sport;
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

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

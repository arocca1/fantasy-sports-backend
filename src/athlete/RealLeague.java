package athlete;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "REAL_LEAGUES" )
public class RealLeague {
	public static long NO_INDIVIDUAL_LEAGUE = -1;
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private Date createdAt;
	private long sportId;

	public RealLeague() { }

	public RealLeague(String name, long sportId) {
		this.name = name;
		this.sportId = sportId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSportId() {
		return sportId;
	}

	public void setSportId(long sportId) {
		this.sportId = sportId;
	}

	public int getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

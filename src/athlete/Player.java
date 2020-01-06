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
@Table( name = "PLAYERS" )
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	// TODO add retired / active
	private Date createdAt = Calendar.getInstance().getTime();
	@ManyToOne
	@JoinColumn(name = "positionId", nullable = false)
	private Position position;
	@ManyToOne
	@JoinColumn(name = "realTeamId", nullable = false)
	private RealTeam realTeam;

	public Player() { }

	public Player(String name, Position position, RealTeam realTeam) {
		this.name = name;
		this.position = position;
		this.realTeam = realTeam;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RealTeam getRealTeam() {
		return realTeam;
	}

	public void setRealTeam(RealTeam realTeam) {
		this.realTeam = realTeam;
	}

	public long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Position getPosition() {
		return position;
	}
}

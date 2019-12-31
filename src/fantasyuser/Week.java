package fantasyuser;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "WEEKS" )
public class Week {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int num;
	private Date startDate;
	private Date createdAt = Calendar.getInstance().getTime();
	private long seasonId;

	public Week() { }

	public Week(int num, Date startDate, long seasonId) {
		this.num = num;
		this.startDate = startDate;
		this.seasonId = seasonId;
	}
}

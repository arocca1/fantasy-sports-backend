package fantasyuser;

public class Matchup {
	private long id;
	private long matchupDate;
	private long team1Id;
	private long team2Id;
	private long winningTeamId;
	private long losingTeamId;

	public Matchup(long matchupDate, long team1Id, long team2Id) {
		this.matchupDate = matchupDate;
		this.team1Id = team1Id;
		this.team2Id = team2Id;
	}
}

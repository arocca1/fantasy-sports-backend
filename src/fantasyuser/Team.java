package fantasyuser;

public class Team {
	public static long NO_TEAM_ID = -1;
	private long id;
	private String name;
	private long divisionId;
	private long leagueId;

	public Team(String name, long divisionId, long leagueId) {
		this.name = name;
		this.divisionId = divisionId;
		this.leagueId = leagueId;
	}

	public Team(String name, long leagueId) {
		this(name, Division.NO_DIVISION_ID, leagueId);
	}
}

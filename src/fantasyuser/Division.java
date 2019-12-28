package fantasyuser;

public class Division {
	public static long NO_DIVISION_ID = -1;
	private long id;
	private String name;
	private long leagueId;

	public Division(String name, long leagueId) {
		this.name = name;
		this.leagueId = leagueId;
	}
}

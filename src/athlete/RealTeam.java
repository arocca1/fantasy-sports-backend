package athlete;

public class RealTeam {
	private long id;
	private String name;
	private long realLeagueId;

	public RealTeam(String name, long realLeagueId) {
		this.name = name;
		this.realLeagueId = realLeagueId;
	}
}

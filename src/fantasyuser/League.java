package fantasyuser;

import athlete.RealLeague;

public class League {
	private long id;
	private String name;
	private long sportId;
	private long realLeagueId;

	public League(String name, long sportId, long realLeagueId) {
		this.name = name;
		this.sportId = sportId;
		this.realLeagueId = realLeagueId;
	}

	// Imagine doing a soccer fantasy league that we don't want to limit by individual real league
	public League(String name, long sportId) {
		this(name, sportId, RealLeague.NO_INDIVIDUAL_LEAGUE);
	}
}

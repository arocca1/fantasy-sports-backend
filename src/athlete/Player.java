package athlete;

import fantasyuser.Team;

public class Player {
	private long id;
	private String name;
	private long realTeamId;
	private long teamId;

	public Player(String name, long realTeamId, long teamId) {
		this.name = name;
		this.realTeamId = realTeamId;
		this.teamId = teamId;
	}

	public Player(String name, long realTeamId) {
		this(name, realTeamId, Team.NO_TEAM_ID);
	}
}

package athlete;

public class RealLeague {
	public static long NO_INDIVIDUAL_LEAGUE = -1;
	private int id;
	private String name;
	private long sportId;

	public RealLeague(String name, long sportId) {
		this.name = name;
		this.sportId = sportId;
	}
}

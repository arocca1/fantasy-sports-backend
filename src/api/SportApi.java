package api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import athlete.Player;
import athlete.Position;
import athlete.RealLeague;
import athlete.RealTeam;
import athlete.Sport;
import database.JpaPlayerDao;
import database.JpaPositionDao;
import database.JpaRealLeagueDao;
import database.JpaRealTeamDao;
import database.JpaSportDao;

public class SportApi {
	private static final JpaSportDao sportDao = new JpaSportDao();
	private static final JpaRealLeagueDao realLeagueDao = new JpaRealLeagueDao();
	private static final JpaRealTeamDao realTeamDao = new JpaRealTeamDao();
	private static final JpaPlayerDao playerDao = new JpaPlayerDao();
	private static final JpaPositionDao positionDao = new JpaPositionDao();

	/*
	 * JSON expected to be of the following example forms:
	 * { name: "Football" }
	 * {
	 *   name: "Football",
	 *   leagues: [
	 *      {
	 *        name: "NFL",
	 *        teams: [
	 *          { name: "Arizona Cardinals", players: [
	 *            { name: "Patrick Peterson", position: "CB" },
	 *            ...
	 *          ]},
	 *          { name: "Chicago Bears", players: [
	 *          ]},
	 *          ...
	 *        ],
	 *      },
	 *   ],
	 * }
	 */
	public static boolean createNewSport(String nameOrSportJson, boolean isJson) throws JsonMappingException, JsonProcessingException {
		json.Sport parsedSport = null;
		if (isJson) {
			ObjectMapper mapper = new ObjectMapper();
			parsedSport = mapper.readValue(nameOrSportJson, json.Sport.class);
		}
		String name = isJson ? parsedSport.getName() : nameOrSportJson;
		Optional<Sport> sportOptional = sportDao.get(name);
		if (sportOptional.isEmpty()) {
			Sport sport = new Sport(name);
			sportDao.save(sport);
			Map<String, Position> positionNamesToPositions = new HashMap<String, Position>();
			if (isJson) {
				parsedSport.getLeagues().forEach(leagueInfo -> {
					RealLeague realLeague = new RealLeague(leagueInfo.getName(), sport);
					realLeagueDao.save(realLeague);
					leagueInfo.getTeams().forEach(teamInfo -> {
						RealTeam realTeam = new RealTeam(teamInfo.getName(), realLeague);
						realTeamDao.save(realTeam);
						teamInfo.getPlayers().forEach(playerInfo -> {
							String positionName = playerInfo.getPosition();
							Position position = null;
							if (positionNamesToPositions.containsKey(positionName)) {
								position = positionNamesToPositions.get(positionName);
							} else {
								position = new Position(positionName, sport);
								positionDao.save(position);
							}
							Player player = new Player(playerInfo.getName(), position, realTeam);
							playerDao.save(player);
						});
					});
				});
			}
			return true;
		} else {
			throw new RuntimeException("This sport already exists");
		}
	}

	public static boolean createNewSport(String name) throws JsonMappingException, JsonProcessingException {
		return createNewSport(name, false);
	}
}

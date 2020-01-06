package api;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

public class RealLeagueApi {
	private static final JpaSportDao sportDao = new JpaSportDao();
	private static final JpaRealLeagueDao realLeagueDao = new JpaRealLeagueDao();
	private static final JpaRealTeamDao realTeamDao = new JpaRealTeamDao();
	private static final JpaPlayerDao playerDao = new JpaPlayerDao();
	private static final JpaPositionDao positionDao = new JpaPositionDao();

	private static boolean doCreateNewRealLeague(Sport sport, String nameOrRealLeagueJson, boolean isJson) throws JsonMappingException, JsonProcessingException {
		json.RealLeague parsedLeague = null;
		if (isJson) {
			ObjectMapper mapper = new ObjectMapper();
			parsedLeague = mapper.readValue(nameOrRealLeagueJson, json.RealLeague.class);
		}
		String name = isJson ? parsedLeague.getName() : nameOrRealLeagueJson;
		Optional<RealLeague> realLeagueOptional = realLeagueDao.get(sport.getId(), name);
		if (realLeagueOptional.isEmpty()) {
			List<Position> positions = positionDao.getPositionsForSport(sport.getId());
			Map<String, Position> positionNamesToPositions = positions.stream().collect(
					Collectors.toMap(x -> x.getName(), x -> x));
			RealLeague realLeague = new RealLeague(name, sport);
			realLeagueDao.save(realLeague);
			parsedLeague.getTeams().forEach(teamInfo -> {
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
		} else {
			throw new RuntimeException("This league already exists");
		}
		return true;
	}

	/*
	 * JSON expected to be of the following example form:
	 * {
	 *   name: "NFL",
	 *   teams: [
	 *     { name: "Arizona Cardinals", players: [
	 *       { name: "Patrick Peterson", position: "CB" },
	 *       ...
	 *     ]},
	 *     { name: "Chicago Bears", players: [
	 *     ]},
	 *     ...
	 *   ],
	 * }
	 */
	public static boolean createNewRealLeague(long sportId, String nameOrRealLeagueJson, boolean isJson) throws JsonMappingException, JsonProcessingException {
		Optional<Sport> sportOptional = sportDao.get(sportId);
		if (!sportOptional.isEmpty()) {
			Sport sport = sportOptional.get();
			return doCreateNewRealLeague(sport, nameOrRealLeagueJson, isJson);
		} else {
			throw new RuntimeException("This sport does not exist");
		}
	}

	public static boolean createNewRealLeague(long sportId, String name) throws JsonMappingException, JsonProcessingException {
		return createNewRealLeague(sportId, name, false);
	}

	public static boolean createNewRealLeague(String sportName, String nameOrRealLeagueJson, boolean isJson) throws JsonMappingException, JsonProcessingException {
		Optional<Sport> sportOptional = sportDao.get(sportName);
		if (!sportOptional.isEmpty()) {
			Sport sport = sportOptional.get();
			return doCreateNewRealLeague(sport, nameOrRealLeagueJson, isJson);
		} else {
			throw new RuntimeException("This sport does not exist");
		}
	}

	public static boolean createNewRealLeague(String sportName, String name) throws JsonMappingException, JsonProcessingException {
		return createNewRealLeague(sportName, name, false);
	}
}

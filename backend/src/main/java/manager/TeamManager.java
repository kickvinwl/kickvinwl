package manager;

import entities.League;
import entities.Team;
import persistence.TeamPersistenceService;
import util.TeamDeserializer;

import javax.persistence.NoResultException;
import java.util.List;

public class TeamManager {

    // Konfigurationsparameter
    private final String API_URL = "https://www.openligadb.de/api/getavailableteams/bl1/";
    private String apiParameter;
    private League league;

    public TeamManager(League league) {
        this.league = league;
    }

    /**
     * Persistiere die Teams aus der API
     */
    public void persistTeams() {
        try{
            List<Team> teamList = getAllTeamsFromAPI();
            TeamPersistenceService tps = TeamPersistenceService.getInstance();
            for (Team team : teamList) {
                try {
                    tps.getByTeamId(team.getTeamId());
                } catch (NoResultException e) {
                    tps.save(team);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Erhalte alle Teams aus der API
     *
     * @return Liste aller Teams
     * @throws Exception
     */
    private List<Team> getAllTeamsFromAPI() throws Exception{
        apiParameter = league.getSeason();
        TeamDeserializer td = new TeamDeserializer();
        return td.deserializeTeam(API_URL +apiParameter);
    }

}

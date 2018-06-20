package manager;

import entities.League;
import entities.Matchday;
import persistence.MatchPersistenceService;
import persistence.MatchdayPersistenceService;
import util.MatchDayDeserializier;

import javax.persistence.NoResultException;
import java.util.List;

public class MatchDayManager {

    private final String apiURL = "https://www.openligadb.de/api/getavailablegroups/";
    private String apiParameter;
    private League league;

    public MatchDayManager(League league) {
        apiParameter = String.valueOf(league.getCurrentMatchday());
        this.league = league;
    }

    public Matchday getCurrentMatchday() throws Exception{
        String currentURL = "https://www.openligadb.de/api/getcurrentgroup/bl1";
        MatchDayDeserializier mdd = new MatchDayDeserializier();
        return mdd.deserializeCurrent(currentURL, league);
    }

    public List<Matchday> getMatchDaysFromAPI() throws Exception {
        MatchDayDeserializier mdd = new MatchDayDeserializier();
        apiParameter = String.valueOf(league.getLeagueId()+"/"+league.getSeason());
        List<Matchday> matchdays = mdd.deserializeMatchDays(apiURL+apiParameter,this.league);
        matchdays.forEach(matchday -> matchday.setLeague(this.league));
        return matchdays;
    }

    /**
     * TODO
     * @return
     * @throws NoResultException catch this one and call updateData in catch clause
     */
    public List<Matchday> getMatchDaysFromDatabase() throws NoResultException {
        return null;
    }



    /**
     *
     * @return true if method terminated successfully; if not return false
     * @throws Exception
     */
    public boolean updateData() throws Exception {
        MatchdayPersistenceService mdps = MatchdayPersistenceService.getInstance();
        try {
            getMatchDaysFromDatabase().forEach(matchDay -> mdps.delete(matchDay));
            getMatchDaysFromAPI().forEach(matchDay -> mdps.save(matchDay));
            return true;
        } catch (NoResultException e) {
            try {
                getMatchDaysFromAPI().forEach(matchDay -> mdps.save(matchDay));
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }
}
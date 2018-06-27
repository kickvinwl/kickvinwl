package manager;

import entities.League;
import entities.Match;
import persistence.MatchPersistenceService;
import util.MatchDeserializer;

import javax.persistence.NoResultException;
import java.util.List;

public class MatchManager {

    private final String apiUrl = "https://www.openligadb.de/api/getmatchdata/";
    private String apiParameter;
    private League league;

    public MatchManager(League league) {
        this.league = league;
    }

    public void persistAllMatchesFromAPI() throws Exception{
        List<Match> allMatches = getAllMatchesFromAPI();
        for (Match match : allMatches) {
            if(MatchPersistenceService.getInstance().exists(match.getExternalMatchID())) {
                MatchPersistenceService.getInstance().update(match);
            } else {
                MatchPersistenceService.getInstance().save(match);
            }
        }
    }

    /**
     *
     * @param matchDay the real number of the matchday from 1 - 34
     * @throws Exception
     */
    public void persistAllMatchesForMatchDayFromAPI(final int matchDay) throws Exception{
        List<Match> allMatches = getAllMatchesForMatchdayFromAPI(matchDay);
        for (Match match : allMatches) {
            if(MatchPersistenceService.getInstance().exists(match.getExternalMatchID())) {
                MatchPersistenceService.getInstance().update(match);
            } else {
                MatchPersistenceService.getInstance().save(match);
            }
        }
    }

    public List<Match> getMatchesForCurrentMatchdayFromAPI() throws Exception{
        apiParameter = String.valueOf(league.getLeagueId());
        MatchDeserializer md = new MatchDeserializer();
        return md.deserializeMatches(apiUrl+apiParameter);
    }

    private List<Match> getAllMatchesFromAPI() throws Exception{
        apiParameter = String.valueOf(league.getLeagueId()+"/"+league.getSeason());
        MatchDeserializer md = new MatchDeserializer();
        return md.deserializeMatches(apiUrl+apiParameter);
    }

    private List<Match> getAllMatchesForMatchdayFromAPI(final int matchDay) throws Exception{
        apiParameter = String.valueOf(league.getLeagueId()+league.getSeason()+matchDay);
        MatchDeserializer md = new MatchDeserializer();
        return md.deserializeMatches(apiUrl+apiParameter);
    }

    public List<Match> getAllMatchesFromDatabase() {
        return MatchPersistenceService.getInstance().getAllMatches();
    }

    public List<Match> getAllMatchesForMatchDayFromDatabase(final int matchdayID) {
        return MatchPersistenceService.getInstance().getAllMatchesForMatchDay(matchdayID);
    }

}

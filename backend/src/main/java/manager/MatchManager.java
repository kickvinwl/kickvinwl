package manager;

import entities.League;
import entities.Match;
import persistence.MatchPersistenceService;
import util.MatchDeserializer;

import java.util.List;

/**
 * Verwaltungsklasse für die Elemente der einzelnen Spiele
 *
 * Zugehörige Aufgaben sind das Verwalten der einzelnen Spielelemente.
 * Dazu gehört das Laden der Daten aus der API und das Austauschen der lokalen Daten durch neue.
 */
public class MatchManager {

    // Konfigurationsparameter
    private final String API_URL = "https://www.openligadb.de/api/getmatchdata/";
    private String apiParameter;
    private League league;

    /**
     * Konstruktor
     *
     * @param league wird verwendet um die parameter für die Season zu erhalten
     */
    public MatchManager(League league) {
        this.league = league;
    }

    /**
     * lade die Spiele aus der API
     *
     * @return Liste aller Spiele einer Season
     * @throws Exception
     */
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
     * Persistiere alle Spiele eines Spieltages
     * @param matchDay die Spieltagsnummer zwischen 1 und 34
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

    /**
     * Persistiere alle Spiele des aktuellen Spieltages
     * @throws Exception
     */
    public List<Match> getMatchesForCurrentMatchdayFromAPI() throws Exception{
        apiParameter = String.valueOf(league.getLeagueId());
        MatchDeserializer md = new MatchDeserializer();
        return md.deserializeMatches(API_URL +apiParameter);
    }

    /**
     * lade alle Spiele aus der API
     * @return Liste aller Spiele
     * @throws Exception
     */
    private List<Match> getAllMatchesFromAPI() throws Exception{
        apiParameter = String.valueOf(league.getLeagueId()+"/"+league.getSeason());
        MatchDeserializer md = new MatchDeserializer();
        return md.deserializeMatches(API_URL +apiParameter);
    }

    /**
     * lade alle Spiele eines Spieltages
     * @param matchDay die Spieltagsnummer zwischen 1 und 34
     * @throws Exception
     */
    private List<Match> getAllMatchesForMatchdayFromAPI(final int matchDay) throws Exception{
        apiParameter = String.valueOf(league.getLeagueId()+league.getSeason()+matchDay);
        MatchDeserializer md = new MatchDeserializer();
        return md.deserializeMatches(API_URL +apiParameter);
    }

    /**
     * lade alle Spiele aus der Datenbank
     * @return Liste aller Spiele
     */
    public List<Match> getAllMatchesFromDatabase() {
        return MatchPersistenceService.getInstance().getAllMatches();
    }

    /**
     * lade alle Spiele eines Spieltages aus der Datenbank
     *
     * @param matchdayID die Spieltagsnummer zwischen 1 und 34
     * @return Liste aller Spiele
     */
    public List<Match> getAllMatchesForMatchDayFromDatabase(final int matchdayID) {
        return MatchPersistenceService.getInstance().getAllMatchesForMatchDay(matchdayID);
    }

}

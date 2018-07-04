package manager;

import entities.League;
import entities.Matchday;
import persistence.MatchdayPersistenceService;
import util.MatchDayDeserializier;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Verwaltungsklasse für die Elemente der Spieltage
 *
 * Zugehörige Aufgaben sind das Verwalten der einzelnen Spieltagselemente.
 * Dazu gehört das Laden der Daten aus der API und das Austauschen der lokalen Daten durch neue.
 */
public class MatchDayManager {

    // Konfigurationsparameter
    private final String API_URL = "https://www.openligadb.de/api/getavailablegroups/";
    private final String API_URL_CURRENT_MATCHDAY = "https://www.openligadb.de/api/getcurrentgroup/bl1";
    private String apiParameter;
    private League league;

    /**
     * Konstruktor
     *
     * @param league wird verwendet um die parameter für die Season zu erhalten
     */
    public MatchDayManager(League league) {
        apiParameter = String.valueOf(league.getCurrentMatchday()); // TODO
        this.league = league;
    }

    /**
     * Erhalte den aktuellen Spieltag aus der API
     * @return den aktuellen Spieltag
     * @throws Exception
     */
    public Matchday getCurrentMatchday() throws Exception{
        MatchDayDeserializier mdd = new MatchDayDeserializier();
        Matchday currentMatchday = mdd.deserializeCurrent(API_URL_CURRENT_MATCHDAY, league);
        return MatchdayPersistenceService.getInstance().getMatchdayByExternalId(currentMatchday.getExternalMatchDayID());
    }

    /**
     * lade alle Matchdays aus der API
     * @return alle Spieltage der Season
     * @throws Exception
     */
    public List<Matchday> getMatchDaysFromAPI() throws Exception {
        MatchDayDeserializier mdd = new MatchDayDeserializier();
        apiParameter = String.valueOf(league.getLeagueId()+"/"+league.getSeason());
        List<Matchday> matchdays = mdd.deserializeMatchDays(API_URL +apiParameter,this.league);
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
     * aktualisiere die lokalen Daten mit den Daten aus der externen API
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

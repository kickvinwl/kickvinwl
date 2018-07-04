package manager;

import entities.BundesligaTable;
import entities.League;
import persistence.BundesligaTablePersistenceService;
import resources.datamodel.BundesligaTableTransform;
import util.BundesligaTableDeserializer;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Verwaltungsklasse für die Elemente der Bundesligatabelle
 *
 * Zugehörige Aufgaben sind das Verwalten der einzelnen Bundesligaelemente.
 * Dazu gehört das Laden der Daten aus der API und das Austauschen der lokalen Daten durch neue.
 */
public class BundesligaTableManager {

    // Konfigurationsparameter
    private final String API_URL = "https://www.openligadb.de/api/getbltable/";
    private String apiParameters;
    private League league;

    /**
     * Konstruktor
     *
     * @param league wird verwendet um die parameter für die Season zu erhalten
     */
    public BundesligaTableManager(League league) {
        apiParameters = String.valueOf(league.getLeagueId()) + "/" + league.getSeason().toString();
        this.league = league;
    }

    /**
     * lade die Bundesligatabelle aus der API
     *
     * @return Liste aller Elemente in der Bundesligatabelle
     * @throws Exception
     */
    private List<BundesligaTable> getBundesligatableFromAPI() throws Exception {
        BundesligaTableDeserializer bltd = new BundesligaTableDeserializer();
            List<BundesligaTable> ble = bltd.deserializeBundesligaTable(API_URL +apiParameters);
            ble.forEach( b -> b.setLeague(league) );
            return ble;
    }

    /**
     * laden der Bundesligatabellendaten aus der lokalen Datenbank
     *
     * @return Liste aller Elemente in der Bundesligatabelle
     */
    private List<BundesligaTable> getBundesligatableFromDatabase() throws NoResultException{
        List<BundesligaTable> bl = BundesligaTablePersistenceService.getInstance().getAllEntriesByLeagueId(league.getId());
        return bl;
    }

    /**
     *  aktualisiere die lokalen Daten mit den Daten aus der externen API
     */
    public void updateData() throws Exception {
        try {
            getBundesligatableFromDatabase().forEach(u -> BundesligaTablePersistenceService.getInstance().delete(u));
            getBundesligatableFromAPI().forEach(u ->BundesligaTablePersistenceService.getInstance().save(u));
        } catch (NoResultException e){
            getBundesligatableFromAPI().forEach(u ->BundesligaTablePersistenceService.getInstance().save(u));
        }
    }

    //TODO: refactor try/catch stuff

    /**
     * Transformiere die Daten zur weiterreichung an das Frontend
     * @return
     */
    public BundesligaTableTransform getTransform() {
        try {
            List<BundesligaTable> blt = getBundesligatableFromDatabase();
            return new BundesligaTableTransform(blt);
        } catch (NoResultException e) {
            try {
                getBundesligatableFromAPI().forEach(u -> BundesligaTablePersistenceService.getInstance().save(u));
                List<BundesligaTable> blt = getBundesligatableFromDatabase();
                return new BundesligaTableTransform(blt);
            } catch (Exception e1) {
                return null;
            }
        }
    }

}

package manager;

import entities.BundesligaTable;
import entities.League;
import persistence.BundesligaTablePersistenceService;
import util.BundesligaTableDeserializer;

import javax.persistence.NoResultException;
import java.util.List;

public class BundesligaTableManager {

    private final String apiURL = "https://www.openligadb.de/api/getbltable/";
    private String apiParameters;
    private League league;

    /**
     * 
     * @param league
     */
    public BundesligaTableManager(League league) {
        apiParameters = String.valueOf(league.getLeagueId()) + "/" + league.getSeason().toString();
        this.league = league;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    private List<BundesligaTable> getBundesligatableFromAPI() throws Exception {
        BundesligaTableDeserializer bltd = new BundesligaTableDeserializer();
            List<BundesligaTable> ble = bltd.deserializeBundesligaTable(apiURL+apiParameters);
            ble.forEach( b -> b.setLeague(league) );
            return ble;
    }

    /**
     *
     * @return
     */
    private List<BundesligaTable> getBundesligatableFromDatabase() {
        List<BundesligaTable> bl = BundesligaTablePersistenceService.getInstance().getAllEntriesByLeagueId(league.getId());
        return bl;
    }

    /**
     *
     */
    public void updateData() throws Exception {
        try {
            getBundesligatableFromDatabase().forEach(u -> BundesligaTablePersistenceService.getInstance().delete(u));
            getBundesligatableFromAPI().forEach(u ->BundesligaTablePersistenceService.getInstance().save(u));
        } catch (NoResultException e){
            getBundesligatableFromAPI().forEach(u ->BundesligaTablePersistenceService.getInstance().save(u));
        }

    }

}

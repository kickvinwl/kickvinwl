package manager;

import entities.BundesligaTable;
import entities.League;
import persistence.BundesligaTablePersistenceService;
import resources.datamodel.BundesligaTableTransform;
import util.BundesligaTableDeserializer;

import javax.persistence.NoResultException;
import java.util.List;

public class BundesligaTableManager {

    private final String API_URL = "https://www.openligadb.de/api/getbltable/";
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
            List<BundesligaTable> ble = bltd.deserializeBundesligaTable(API_URL +apiParameters);
            ble.forEach( b -> b.setLeague(league) );
            return ble;
    }

    /**
     *
     * @return
     */
    private List<BundesligaTable> getBundesligatableFromDatabase() throws NoResultException{
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

    //TODO: refactor try/catch stuff
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

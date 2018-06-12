package persistence;

import entities.BundesligaTable;
import entities.League;
import util.BundesligaTableDeserializer;

import java.util.List;

public class BundesligaTableManager {

    private final String apiURL = "https://www.openligadb.de/api/getbltable/bl1/2017";
    private String apiParameters;
    private League league;

    public BundesligaTableManager(League league) {
        apiParameters = league.getLeagueId().toString() + "/" + league.getSeason().toString();
        this.league = league;
    }

    private void getBundesligatableFromAPI() {
        BundesligaTableDeserializer bltd = new BundesligaTableDeserializer();
        try {
            List<BundesligaTable> ble = bltd.deserializeBundesligaTable(apiURL+apiParameters);
            for (BundesligaTable b : ble) {

            }
        } catch (Exception e) {
        }
    }

    private void getBundesligatableFromDatabse() {
        List<BundesligaTable> bl = BundesligaTablePersistenceService.getInstance().getAllEntriesByLeagueId(league.getId());
    }


}

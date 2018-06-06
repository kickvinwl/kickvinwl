package util;


import com.google.gson.JsonDeserializer;
import entities.BundesligaTable;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import entities.Team;
import persistence.TeamPersistenceService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
public class BundesligaTableDeserializer {
    //https://www.openligadb.de/api/getbltable/bl1/2017

    JsonDeserializer<BundesligaTable> deserializer = new JsonDeserializer<BundesligaTable>() {
        @Override
        public BundesligaTable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            BundesligaTable bl = new BundesligaTable();
            bl.setMatches(jsonObject.get("Matches").getAsInt());
            bl.setWins(jsonObject.get("Won").getAsInt());
            bl.setDraws(jsonObject.get("Draw").getAsInt());
            bl.setLosses(jsonObject.get("Lost").getAsInt());
            bl.setPoints(jsonObject.get("Points").getAsInt());
            bl.setGoals(jsonObject.get("Goals").getAsInt());
            bl.setOpponentGoals(jsonObject.get("OpponentGoals").getAsInt());
            bl.setGoalDifference();

            //TODO: team instanz beschaffen, eventuell an anderer Stelle
            //bl.setTeam(TeamPersistenceService.getInstance().getByTeamId(jsonObject.get("TeamInfoId").getAsInt()));
            //TODO: liga setzen
            //bl.setLeague()
            //TODO: Position bei iteration setzen
            //bl.setLeaguePosition()

            return bl;
        }

    };

    /**
     *
     * @param bundesligaURL
     * @return
     * @throws Exception
     */
    public List<BundesligaTable> deserializeBundesligaTable(String bundesligaURL) throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BundesligaTable.class, deserializer);

        Gson customGson = gsonBuilder.create();
        String json = URLtoJSON.readUrl(bundesligaURL);
        BundesligaTable[] blA = customGson.fromJson(json, BundesligaTable[].class);
        List<BundesligaTable> bligaEntries = Arrays.asList(blA);
        return bligaEntries;
    }
}

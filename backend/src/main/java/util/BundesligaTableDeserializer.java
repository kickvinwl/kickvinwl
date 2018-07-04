package util;


import com.google.gson.JsonDeserializer;
import entities.BundesligaTable;
import com.google.gson.*;
import persistence.TeamPersistenceService;

import javax.persistence.NoResultException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Klasse zur Deserialisierung der Bundesligatabelle im JSON-Format aus der externen API
 * Verwendet wurde Gson als Treiber für die deserializierung
 */
public class BundesligaTableDeserializer {

    /**
     * Diese Funktion definiert die grundlegenden Abläufe des Deserialisers beim extrahieren der Daten aus dem erhaltenem JSONObject.
     * Dazu zählt die Definition der einzelnen Elementnamen, sowie der Datentyp, als welcher der übergebene Wert verarbeitet werden soll.
     * Zusätzlich soll das extrahierte Element der TeamInfoID auf ein Team in den vorhandenen Datensätzen gemapped werden.
     */
    JsonDeserializer<BundesligaTable> deserializer = new JsonDeserializer<BundesligaTable>() {
        @Override
        public BundesligaTable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            // Erzeugen eines Dummy-Objects
            BundesligaTable bl = new BundesligaTable();

            // Setzen der einzelnen Parameter auf dem Object
            bl.setMatches(jsonObject.get("Matches").getAsInt());
            bl.setWins(jsonObject.get("Won").getAsInt());
            bl.setDraws(jsonObject.get("Draw").getAsInt());
            bl.setLosses(jsonObject.get("Lost").getAsInt());
            bl.setPoints(jsonObject.get("Points").getAsInt());
            bl.setGoals(jsonObject.get("Goals").getAsInt());
            bl.setOpponentGoals(jsonObject.get("OpponentGoals").getAsInt());
            bl.setGoalDifference();

            // Attribut zur Überprüfung der Notwendigkeit eines Matchings über den Namen eines Teams anstatt die externe ID
            // Dies ist aufgrund eines Fehlers in der externen API notwendig
            boolean tryName = false;

            try {
                // Suchen des Datensatzes eines Teams über die übergebene ID
                bl.setTeam(TeamPersistenceService.getInstance().getByTeamId(jsonObject.get("TeamInfoId").getAsInt()));
            } catch (NoResultException e) {
                // Da kein Team über die ID gefunden wurde, wird das Prüfattribut auf true gesetzt
                tryName = true;
            }
            if (tryName) {
                // Matching des Teams über den übergebenen Namen
                bl.setTeam(TeamPersistenceService.getInstance().getByTeamName(jsonObject.get("TeamName").getAsString()));
            }
            return bl;
        }
    };

    /**
     * Erzeuge eine Liste von Objekten aus den Informationen der externen API
     *
     * @param bundesligaURL URL über welche auf die API zugegriffen wird
     * @return Liste der deserialisierten Objekte
     * @throws Exception
     */
    public List<BundesligaTable> deserializeBundesligaTable(String bundesligaURL) throws Exception {
        // Konfiguriere JSON-Builder mit Zielobjekt und serialisierungsinformationen
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BundesligaTable.class, deserializer);
        Gson customGson = gsonBuilder.create();
        // Beziehe JSON von externer API
        String json = URLtoJSON.readUrl(bundesligaURL);
        // Erzeuge aus dem externen JSON Objekte und verpacke diese in ein Array
        BundesligaTable[] blA = customGson.fromJson(json, BundesligaTable[].class);
        List<BundesligaTable> bligaEntries = Arrays.asList(blA);
        // Position setzen
        for(int i = 1; i <= bligaEntries.size(); i++){
            bligaEntries.get(i-1).setLeaguePosition(i);
        }
        return bligaEntries;
    }
}

package util;

import com.google.gson.*;
import entities.Team;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Klasse zur Deserialisierung der Teams einer Season im JSON-Format aus der externen API
 * Verwendet wurde Gson als Treiber für die Deserializierung
 */
public class TeamDeserializer {

    /**
     * Diese Funktion definiert die grundlegenden Abläufe des Deserialisers beim extrahieren der Daten aus dem erhaltenem JSONObject.
     * Dazu zählt die Definition der einzelnen Elementnamen, sowie der Datentyp, als welcher der übergebene Wert verarbeitet werden soll.
     */
    JsonDeserializer<Team> deserializer = new JsonDeserializer<Team>() {
        @Override
        public Team deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();

            Team team = new Team();
            team.setTeamId(jsonObject.get("TeamId").getAsInt());
            team.setTeamIconURL(jsonObject.get("TeamIconUrl").getAsString());
            team.setTeamName(jsonObject.get("TeamName").getAsString());

            return team;
        }
    };

    /**
     * Erzeuge eine Liste von Objekten aus den Informationen der externen API
     *
     * @param teamURL URL über welche auf die API zugegriffen wird
     * @return Liste der deserialisierten Objekte
     * @throws Exception
     */
    public List<Team> deserializeTeam(String teamURL) throws Exception {
        // Konfiguriere JSON-Builder mit Zielobjekt und serialisierungsinformationen
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Team.class, deserializer);
        Gson customGson = gsonBuilder.create();
        // Beziehe JSON von externer API
        String json = URLtoJSON.readUrl(teamURL);
        // Erzeuge aus dem externen JSON Objekte und verpacke diese in ein Array
        Team[] teamsA = customGson.fromJson(json, Team[].class);
        List<Team> teams = Arrays.asList(teamsA);
        return teams;
    }

}

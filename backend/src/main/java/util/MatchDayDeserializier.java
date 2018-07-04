package util;

import com.google.gson.JsonDeserializer;
import com.google.gson.*;
import entities.League;
import entities.Matchday;
import persistence.LeaguePersistenceService;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * Klasse zur Deserialisierung der Spiele einer Season im JSON-Format aus der externen API
 * Verwendet wurde Gson als Treiber für die deserializierung
 */
public class MatchDayDeserializier {

    /**
     * Diese Funktion definiert die grundlegenden Abläufe des Deserialisers beim extrahieren der Daten aus dem erhaltenem JSONObject.
     * Dazu zählt die Definition der einzelnen Elementnamen, sowie der Datentyp, als welcher der übergebene Wert verarbeitet werden soll.
     */
    JsonDeserializer<Matchday> deserializer = new JsonDeserializer<Matchday>() {
        @Override
        public Matchday deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            Matchday md = new Matchday();
            md.setMatchday(jsonObject.get("GroupOrderID").getAsInt());
            md.setExternalMatchDayID(jsonObject.get("GroupID").getAsInt());
            return md;
        }
    };

    /**
     * Diese Funktion definiert
     *
     * @param matchdayURL zugreifen über die übergeben URL auf die externe API
     * @param league Liga zu welcher die Spieltage zugeordnet werden sollen
     * @return eine Liste aller extrahierten Spieltage
     * @throws Exception
     */
    public List<Matchday> deserializeMatchDays(String matchdayURL, League league) throws Exception{
        System.out.println(matchdayURL);
        List<Matchday> matchdays = deserialize(matchdayURL);
        matchdays.forEach(matchday -> matchday.setLeague(league));
        return matchdays;
    }

    /**
     * Erhalte den aktuellen Spieltag über die API
     * @param singleMatchdayURL zugreifen über die übergeben URL auf die externe API
     * @param league Liga zu welcher der Spieltag zugeordnet werden soll
     * @return
     * @throws Exception
     */
    public Matchday deserializeCurrent(String singleMatchdayURL, League league) throws Exception{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Matchday.class, deserializer);
        Gson customGson = gsonBuilder.create();
        String json = URLtoJSON.readUrl(singleMatchdayURL);
        // Erzeuge aus dem externen JSON Objekte und verpacke diese in ein Array
        Matchday matchday = customGson.fromJson(json, Matchday.class);
        matchday.setLeague(league);
        return matchday;
    }

    /**
     * Erzeuge eine Liste von Objekten aus den Informationen der externen API
     *
     * @param matchdayURL URL über welche auf die API zugegriffen wird
     * @return Liste der deserialisierten Objekte
     * @throws Exception
     */
    private List<Matchday> deserialize(String matchdayURL) throws Exception{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Matchday.class, deserializer);
        Gson customGson = gsonBuilder.create();
        String json = URLtoJSON.readUrl(matchdayURL);
        // Erzeuge aus dem externen JSON Objekte und verpacke diese in ein Array
        Matchday[] blA = customGson.fromJson(json, Matchday[].class);
        List<Matchday> matchdays = Arrays.asList(blA);
        return matchdays;
    }
}

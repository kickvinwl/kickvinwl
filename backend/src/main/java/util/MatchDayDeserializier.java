package util;


import com.google.gson.JsonDeserializer;
import com.google.gson.*;
import entities.League;
import entities.Matchday;
import persistence.LeaguePersistenceService;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
public class MatchDayDeserializier {

    JsonDeserializer<Matchday> deserializer = new JsonDeserializer<Matchday>() {
        @Override
        public Matchday deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            Matchday md = new Matchday();
            md.setMatchday(jsonObject.get("GroupOrderID").getAsInt());
            md.setExternalMatchID(jsonObject.get("GroupID").getAsInt());
            return md;
        }
    };

    /**
     *
     * @param URL to access the desired part of the external API
     * @return a list of all Matchdays from the external API
     * @throws Exception
     */
    public List<Matchday> deserializeMatchDays(String matchdayURL, League league) throws Exception{
        System.out.println(matchdayURL);
        List<Matchday> matchdays = deserialize(matchdayURL);
        matchdays.forEach(matchday -> matchday.setLeague(league));
        return matchdays;
    }

    public List<Matchday> deserializeMatchDays(String matchdayURL, String league_id) throws Exception{
        List<Matchday> matchdays = deserialize(matchdayURL);
        matchdays.forEach(
                matchday -> matchday.setLeague(
                        LeaguePersistenceService.getInstance().getCurrentLeagueByLeagueId(league_id)
                ));
        return matchdays;
    }

    public Matchday deserializeCurrent(String singleMatchdayURL, League league) throws Exception{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Matchday.class, deserializer);
        Gson customGson = gsonBuilder.create();
        String json = URLtoJSON.readUrl(singleMatchdayURL);
        Matchday matchday = customGson.fromJson(json, Matchday.class);
        matchday.setLeague(league);
        return matchday;
    }

    private List<Matchday> deserialize(String matchdayURL) throws Exception{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Matchday.class, deserializer);
        Gson customGson = gsonBuilder.create();
        String json = URLtoJSON.readUrl(matchdayURL);
        Matchday[] blA = customGson.fromJson(json, Matchday[].class);
        List<Matchday> matchdays = Arrays.asList(blA);
        return matchdays;
    }
}

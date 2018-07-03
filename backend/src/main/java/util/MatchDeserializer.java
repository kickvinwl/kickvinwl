package util;

import entities.Match;
import com.google.gson.JsonDeserializer;
import com.google.gson.*;
import entities.Team;
import persistence.MatchdayPersistenceService;
import persistence.TeamPersistenceService;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MatchDeserializer {

    JsonDeserializer<Match> deserializer = new JsonDeserializer<Match>() {
        @Override
        public Match deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject matchObject = json.getAsJsonObject();
            JsonObject groupObject = matchObject.getAsJsonObject("Group");
            JsonObject team1Object = matchObject.getAsJsonObject("Team1");
            JsonObject team2Object = matchObject.getAsJsonObject("Team2");

            Match match = new Match();
            match.setExternalMatchID(matchObject.get("MatchID").getAsInt());
            match.setMatchDateTime(parseDateFromString(matchObject.get("MatchDateTime").getAsString()));
            match.setTeam(getTeamByJsonID(team1Object.get("TeamId").getAsInt()));
            match.setTeam2(getTeamByJsonID(team2Object.get("TeamId").getAsInt()));
            match.setMatchday(MatchdayPersistenceService.getInstance().getMatchdayByExternalId(groupObject.get("GroupID").getAsInt()));
            match.setFinished(matchObject.get("MatchIsFinished").getAsBoolean());

            if (match.isFinished()) {
                JsonArray matchresults = matchObject.getAsJsonArray("MatchResults");
                JsonObject singleResult = matchresults.get(matchresults.size()-1).getAsJsonObject();
                match.setGoalsTeam1(singleResult.get("PointsTeam1").getAsInt());
                match.setGoalsTeam2(singleResult.get("PointsTeam2").getAsInt());
            }
            return match;
        }
    };

    public List<Match> deserializeMatches(String matchdayURL) throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Match.class, deserializer);
        Gson customGson = gsonBuilder.create();
        String json = URLtoJSON.readUrl(matchdayURL);
        Match[] matchesA = customGson.fromJson(json, Match[].class);
        List<Match> matches = Arrays.asList(matchesA);
        return matches;
    }

    private Date parseDateFromString(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return sdf.parse(dateString);
        } catch (ParseException ex) {
            return new Date();
        }
    }

    private Team getTeamByJsonID(int jsonId) {
        return TeamPersistenceService.getInstance().getByTeamId(jsonId);
    }

}

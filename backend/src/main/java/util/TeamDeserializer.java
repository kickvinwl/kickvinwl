package util;

import com.google.gson.*;
import entities.Team;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;


public class TeamDeserializer {

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

    public List<Team> deserializeTeam(String teamURL) throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Team.class, deserializer);
        Gson customGson = gsonBuilder.create();
        String json = URLtoJSON.readUrl(teamURL);
        Team[] teamsA = customGson.fromJson(json, Team[].class);
        List<Team> teams = Arrays.asList(teamsA);
        return teams;
    }

}

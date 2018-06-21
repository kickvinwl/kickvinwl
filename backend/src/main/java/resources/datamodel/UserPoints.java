package resources.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPoints {
    public UserPoints() {

    }


    int platzierung;
    String username;
    int points;

    public UserPoints(int platzierung, String username, Long points) {
        this.platzierung = platzierung;
        this.username = username;
        this.points = points.intValue();
    }

    public UserPoints(int platzierung, String username, int points) {
        this.platzierung = platzierung;
        this.username = username;
        this.points = points;
    }

    @JsonProperty
    public int getPlatzierung() {
        return platzierung;
    }

    @JsonProperty
    public void setPlatzierung(int platzierung) {
        this.platzierung = platzierung;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty
    public int getPoints() {
        return points;
    }

    @JsonProperty
    public void setPoints(int points) {
        this.points = points;
    }
}

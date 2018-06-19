package resources.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TipList {
    private String token;


    private Tip[] matches;

    @JsonProperty
    public String getToken() {
        return token;
    }

    @JsonProperty
    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty
    public Tip[] getMatches() {
        return matches;
    }

    @JsonProperty
    public void setMatches(Tip[] matches) {
        this.matches = matches;
    }
}

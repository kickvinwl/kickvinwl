package resources.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tip {

    public Tip() {

    }


    int matchId;
    int homeTip;
    int awayTip;


    @JsonProperty
    public int getmatchId() {
        return matchId;
    }

    @JsonProperty
    public void setmatchId(int matchId) {
        this.matchId = matchId;
    }

    @JsonProperty
    public int gethomeTip() {
        return homeTip;
    }

    @JsonProperty
    public void sethomeTip(int homeTip) {
        this.homeTip = homeTip;
    }

    @JsonProperty
    public int getawayTip() {
        return awayTip;
    }

    @JsonProperty
    public void setawayTip(int awayTip) {
        this.awayTip = awayTip;
    }


}

package resources.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tip {

    public Tip() {

    }


    int matchId;
    Integer homeTip;
    Integer awayTip;


    @JsonProperty
    public int getmatchId() {
        return matchId;
    }

    @JsonProperty
    public void setmatchId(int matchId) {
        this.matchId = matchId;
    }

    @JsonProperty
    public Integer gethomeTip() {
        return homeTip;
    }

    @JsonProperty
    public void sethomeTip(Integer homeTip) {
        this.homeTip = homeTip;
    }

    @JsonProperty
    public Integer getawayTip() {
        return awayTip;
    }

    @JsonProperty
    public void setawayTip(Integer awayTip) {
        this.awayTip = awayTip;
    }


}

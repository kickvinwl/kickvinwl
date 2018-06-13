package resources.datamodel;

public class Tip {

    public Tip() {

    }

    int matchID;

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public int getTeam1Tip() {
        return team1Tip;
    }

    public void setTeam1Tip(int team1Tip) {
        this.team1Tip = team1Tip;
    }

    public int getTeam2Tip() {
        return team2Tip;
    }

    public void setTeam2Tip(int team2Tip) {
        this.team2Tip = team2Tip;
    }

    int team1Tip;
    int team2Tip;


}

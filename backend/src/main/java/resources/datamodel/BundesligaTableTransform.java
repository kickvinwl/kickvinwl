package resources.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import entities.BundesligaTable;
import persistence.BundesligaTablePersistenceService;
import persistence.TeamPersistenceService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BundesligaTableTransform {

    @JsonProperty
    List<BLTableInfo> teams;

    public BundesligaTableTransform(int leagueID) {
        teams = new ArrayList<>();
        List<BundesligaTable> allTeams = getAllTeams(leagueID);

        allTeams.forEach(blt -> teams.add(new BLTableInfo(blt)));
        setTeams(teams);
    }

    public BundesligaTableTransform(List <BundesligaTable> blt) {
        teams = new ArrayList<>();
        blt.forEach(bl -> teams.add(new BLTableInfo(bl)));
        setTeams(teams);
    }

    private void setTeams(List<BLTableInfo> teams) {
        this.teams = teams;
    }

    private List<BundesligaTable> getAllTeams(int leagueID) {
        BundesligaTablePersistenceService bt = BundesligaTablePersistenceService.getInstance();
        return bt.getAllEntriesByLeagueId(leagueID);
    }

    @Override
    public String toString() {
        return "BundesligaTableTransform{" +
                "teams=" + teams +
                '}';
    }

    private class BLTableInfo {

            @JsonProperty
            int place;
            @JsonProperty
            String imageLink;
            @JsonProperty
            String clubName;
            @JsonProperty
            int games;
            @JsonProperty
            int wins;
            @JsonProperty
            int losses;
            @JsonProperty
            int draws;
            @JsonProperty
            int goals;
            @JsonProperty
            int opponentGoals;
            @JsonProperty
            String difference;
            @JsonProperty
            int points;

            BLTableInfo(BundesligaTable blt) {
                TeamPersistenceService tps = TeamPersistenceService.getInstance();
                this.place = blt.getLeaguePosition();
                this.imageLink = tps.getByTeamId(blt.getTeam().getTeamId()).getTeamIconURL();
                this.clubName = tps.getByTeamId(blt.getTeam().getTeamId()).getTeamName();
                this.games = blt.getMatches();
                this.wins = blt.getWins();
                this.losses = blt.getLosses();
                this.draws = blt.getDraws();
                this.goals = blt.getGoals();
                this.opponentGoals = blt.getOpponentGoals();
                if (blt.getGoalDifference() > 0 ) {
                    this.difference = "+" + String.valueOf(blt.getGoalDifference());
                } else {
                    this.difference = String.valueOf(blt.getGoalDifference());
                }
                this.points = blt.getPoints();
            }

            @Override
            public String toString() {
                return "BLTableInfo{" +
                        "place=" + place +
                        ", imageLink='" + imageLink + '\'' +
                        ", clubName='" + clubName + '\'' +
                        ", games=" + games +
                        ", wins=" + wins +
                        ", losses=" + losses +
                        ", draws=" + draws +
                        ", goals=" + goals +
                        ", difference=" + difference +
                        ", points=" + points +
                        '}';
            }
        }

}

package resources.datamodel;

import entities.Team;

import java.util.Date;
import java.util.List;

public class MatchTips {

    String season;
    String gameday;

    List<MatchWithPoints> matches;

    private class MatchWithPoints{
        int id;
        Date date;
        Team homeTeam;
        Team awayTeam;
        int points;

        private class Team {
            int id;
            String name;
            String logo;
            int tipScore;
            int realScore;
        }
    }
}

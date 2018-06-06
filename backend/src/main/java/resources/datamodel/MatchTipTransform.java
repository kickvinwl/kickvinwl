package resources.datamodel;

import entities.Team;

import java.util.Date;
import java.util.List;

public class MatchTipTransform {

    String season;
    String gameday;

    List<MatchWithPoints> matches;

    public void setSeason(String season) {
        this.season = season;
    }

    public void setGameday(String gameday) {
        this.gameday = gameday;
    }

    public void setMatches(List<MatchWithPoints> matches) {
        this.matches = matches;
    }

    public static class MatchWithPoints{
        int id;
        Date date;
        Team homeTeam;
        Team awayTeam;
        int points;

        public void setId(int id) {
            this.id = id;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public void setHomeTeam(Team homeTeam) {
            this.homeTeam = homeTeam;
        }

        public void setAwayTeam(Team awayTeam) {
            this.awayTeam = awayTeam;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public static class Team {
            int id;
            String name;
            String logo;
            int tipScore;
            int realScore;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public void setTipScore(int tipScore) {
                this.tipScore = tipScore;
            }

            public void setRealScore(int realScore) {
                this.realScore = realScore;
            }
        }
    }
}

package resources.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import entities.*;
import persistence.MatchTipPersistenceService;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MatchTipTransform {

    @JsonProperty
    String gameday;

    @JsonProperty
    String season;

    @JsonProperty
    List<MatchWithPoints> matches;

    /**
     *
     * @param season
     * @param matchday null um alle Spieltage zubekommen
     * @param matchTips
     */
    public MatchTipTransform(String season, Matchday matchday, List<MatchTip> matchTips) {
        this.season = season;
        this.gameday = "" + matchday.getId();

        MatchTipPersistenceService matchTipPersistenceService = MatchTipPersistenceService.getInstance();


        ArrayList<MatchWithPoints> matchesOut = new ArrayList<>();


        for (MatchTip matchTip: matchTips) {
//            MatchTip matchTip = matchTipPersistenceService.getByUserIdAndMatchId(user.getId(), match.getMatchID());
            if(matchday == null || matchTip.getTippedMatch().getMatchday().getMatchday() == matchday.getMatchday() ) {
                matchesOut.add(new MatchWithPoints(matchTip));
            }
        }

        setMatches(matchesOut);
    }

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
        @JsonProperty
        int id;
        @JsonProperty
        Date date;
        @JsonProperty
        Team homeTeam;

        @JsonProperty
        Team awayTeam;

        @JsonProperty
        int points;

        public MatchWithPoints(MatchTip matchTip)
        {
                Match match = matchTip.getTippedMatch();
                setId(match.getId());
                setDate(match.getMatchDateTime());
                setHomeTeam(new Team(matchTip, true));
                setAwayTeam(new Team(matchTip, false));
                setPoints(matchTip.getUserPoints());
        }

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
            @JsonProperty
            int id;
            @JsonProperty
            String name;
            @JsonProperty
            String logo;
            @JsonProperty
            int tipScore;
            @JsonProperty
            int realScore;

            public Team(MatchTip matchTip, boolean isHomeTeam)
            {

                Match match = matchTip.getTippedMatch();

                entities.Team team = (isHomeTeam) ? match.getTeam() : match.getTeam2();

                setId(team.getId());
                setName(team.getTeamName());
                setLogo(team.getTeamIconURL());
                setTipScore((isHomeTeam) ? matchTip.getGoalsTeam1() : matchTip.getGoalsTeam2());
                setRealScore((isHomeTeam) ? match.getGoalsTeam1() : match.getGoalsTeam2());

            }

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

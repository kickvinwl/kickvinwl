package resources.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import entities.*;
import persistence.MatchPersistenceService;
import persistence.MatchTipPersistenceService;
import persistence.MatchdayPersistenceService;

import javax.persistence.Entity;
import javax.persistence.NoResultException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Erzeugt mithilfe von internen Klassen an das Frontend angepasste Datensätze zur darstellung der Verarbeiteten Informationen
 */
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
     * @param user
     */
    public MatchTipTransform(String season, Matchday matchday, User user) {
        this.season = season;
        this.gameday = "" + matchday.getMatchday();


        List<MatchWithPoints> matchesOut = getAllMatchTipsAndFillWithUserData(user, matchday);

        setMatches(matchesOut);
    }

    private List<MatchWithPoints> getAllMatchTipsAndFillWithUserData(User user, Matchday matchday)
    {
        MatchTipPersistenceService matchTipPersistenceService = MatchTipPersistenceService.getInstance();
        MatchPersistenceService mps = MatchPersistenceService.getInstance();
        //Matches vom Tag besorgen
        List<Match> matchesFromDay = mps.getAllMatchesForMatchDay(matchday.getId());
        ArrayList<MatchWithPoints> matchesTipReturm = new ArrayList<>();

        for(Match match : matchesFromDay)
        {
            System.out.println(match);
            MatchTip tip = null;
            try {
                tip = matchTipPersistenceService.getByUserIdAndMatchId(user.getId(), match.getId());
            }catch (NoResultException e)
            {
                tip = new MatchTip();
                tip.setOwner(user);
                tip.setTippedMatch(match);
            }
            matchesTipReturm.add(new MatchWithPoints(tip));
        }

        return matchesTipReturm;
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
        Integer points;

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
            this.points = points == -1 ? null : points;
        }

        public static class Team {

            @JsonProperty
            int id;
            @JsonProperty
            String name;
            @JsonProperty
            String logo;
            @JsonProperty
            Integer tipScore;
            @JsonProperty
            Integer realScore;

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
                this.tipScore = tipScore == -1 ? null : tipScore;
            }

            public void setRealScore(int realScore) {
                this.realScore = realScore == -1 ? null:  realScore ;
            }

        }
    }

    public String getGameday() {
        return gameday;
    }
}

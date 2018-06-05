package entities;

import javax.persistence.*;

/**
 *
 */
@Entity
public class BundesligaTable extends EntityGeneratedKey {

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;
    private int leaguePosition;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    private int matches;
    private int wins;
    private int draws;
    private int losses;
    private int goals;
    private int opponentGoals;
    private int points;

    @Transient
    private int goalDifference;


    /**
     * automatic calculation of the goal difference
     */
    public void setGoalDifference() {
        this.goalDifference = getGoals() - getOpponentGoals();
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public int getLeaguePosition() {
        return leaguePosition;
    }

    public void setLeaguePosition(int leaguePosition) {
        this.leaguePosition = leaguePosition;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getOpponentGoals() {
        return opponentGoals;
    }

    public void setOpponentGoals(int opponentGoals) {
        this.opponentGoals = opponentGoals;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


}

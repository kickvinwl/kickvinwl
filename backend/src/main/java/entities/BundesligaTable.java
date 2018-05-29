package entities;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * G. Back
 */

@Entity
public class BundesligaTable {

    @ManyToOne //TODO: placeholder
    @JoinColumn(name = "league_id")
    private League leagueId;
    private int leaguePosition;
    @ManyToOne //TODO: placeholder
    @JoinColumn(name = "team_id")
    private Team teamId;
    private int matches;
    private int wins;
    private int draws;
    private int losses;
    private int goals;
    private int opponentGoals;
    private int points;

    //TODO: getter/setter generieren

}

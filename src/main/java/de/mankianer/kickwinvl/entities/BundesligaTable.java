package de.mankianer.kickwinvl.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BundesligaTable{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private League league;
    private int leaguePosition;
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


}

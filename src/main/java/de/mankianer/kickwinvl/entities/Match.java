package de.mankianer.kickwinvl.entities;

import lombok.Data;

import java.time.LocalDate;


import javax.persistence.*;

@Entity
@Table(name ="game")
@Data
public class Match{

    @GeneratedValue
    @Id
    private Long matchID;

    @ManyToOne
    private Matchday matchday;

    private LocalDate matchDateTime;

    @ManyToOne
    private Team teamHome;

    @ManyToOne
    private Team teamAway;

    private int goalsTeam1;
    private int goalsTeam2;

    private boolean isFinished;
    /**
     * This number describes the id which is associated with this specific dataset in OpenLigaDB
     */
    private int externalMatchID;
}

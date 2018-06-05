package entities;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
public class Match extends EntityExternalKey{

    private int matchdayId;
    private DateTime matchDateTime;
    @ManyToMany
    @JoinColumn(name = "team1_id")
    private Team team;
    @ManyToMany
    @JoinColumn(name = "team2_id")
    private Team team2;
    private int goalsTeam1;
    private int goalsTeam2;
    private int statusId;

}

package entities;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
public class Match extends EntityExternalKey{

    private int matchdayId;
    private DateTime matchDateTime;
    @ManyToMany
    @JoinColumn(name = "id")
    private Team team1Id;
    @ManyToMany
    @JoinColumn(name = "id")
    private Team team2Id;
    private int goalsTeam1;
    private int goalsTeam2;
    private int statusId;

}

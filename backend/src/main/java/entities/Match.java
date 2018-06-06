package entities;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="game")
public class Match extends EntityGeneratedKey{

    private int matchdayId;
    private Date matchDateTime;


    @JoinColumn(name = "team1_id")
    @Column(columnDefinition="int(11)")
    private Team team;

    @JoinColumn(name = "team2_id")
    @Column(columnDefinition="int(11)")
    private Team team2;


    @Column(updatable = true, nullable = true)
    @OneToMany(mappedBy = "id")
    private List<MatchTip> tips = new ArrayList<>();

    private int goalsTeam1;

    private int goalsTeam2;
    private int statusId;
    /**
     * This number describes the id which is associated with this specific dataset in OpenLigaDB
     */
    private int matchID;

    public int getMatchdayId() {
        return matchdayId;
    }

    public Date getMatchDateTime() {
        return matchDateTime;
    }

    public List<MatchTip> getTips() {
        return tips;
    }

    public int getGoalsTeam1() {
        return goalsTeam1;
    }

    public int getGoalsTeam2() {
        return goalsTeam2;
    }

    public int getStatusId() {
        return statusId;
    }

    public int getMatchID() {
        return matchID;
    }

    public Team getTeam() {
        return team;
    }

    public Team getTeam2() {
        return team2;
    }
}

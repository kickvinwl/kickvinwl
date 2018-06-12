package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name ="game")
public class Match extends EntityGeneratedKey{

    private int matchdayId;

    @Column(columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date matchDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_team1")
    private Team team;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_team2")
    private Team team2;


    @Column(updatable = true, nullable = true)
    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
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

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }
}

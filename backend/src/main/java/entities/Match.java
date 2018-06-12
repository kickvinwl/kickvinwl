package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name ="game")
public class Match extends EntityGeneratedKey{

    private int matchdayId;
    @Column(columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date matchDateTime;

/*
    @ManyToMany
    @JoinColumn(name = "team1_id")
    private Team team;

    @ManyToMany
    @JoinColumn(name = "team2_id")
    private Team team2;
*/

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

}

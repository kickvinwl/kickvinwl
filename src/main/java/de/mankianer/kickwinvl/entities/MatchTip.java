package de.mankianer.kickwinvl.entities;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Entity
public class MatchTip {

    @Id
    @GeneratedValue
    private Long matchTipID;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Match tippedMatch;

    private Integer goalsTeam1;
    private Integer goalsTeam2;
    private Integer userPoints;

    public MatchTip(User owner, Match tippedMatch, int goalsTeam1, int goalsTeam2)
    {
        this.owner = owner;
        this.tippedMatch = tippedMatch;
        this.goalsTeam1 = goalsTeam1;
        this.goalsTeam2 = goalsTeam2;
    }

    public MatchTip() {
    }
}



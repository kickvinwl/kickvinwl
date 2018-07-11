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

    private Integer goalsTeamHome;
    private Integer goalsTeamAway;
    private Integer userPoints;
}



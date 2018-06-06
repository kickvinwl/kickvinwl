package entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class MatchTip extends EntityGeneratedKey{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_match")
    private Match tippedMatch;

    private int goalsTeam1;
    private int goalsTeam2;
    private int userPoints;

    public int getGoalsTeam1() {
        return goalsTeam1;
    }

    public int getGoalsTeam2() {
        return goalsTeam2;
    }

    public int getUserPoints() {
        return userPoints;
    }
}

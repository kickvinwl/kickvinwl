package entities;

import javax.persistence.*;

@Entity
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

}

package entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class MatchTip extends EntityGeneratedKey {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user")
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_match")
    private Match tippedMatch;

    private int goalsTeam1;
    private int goalsTeam2;
    private int userPoints;

    public MatchTip(User owner, Match tippedMatch, int goalsTeam1, int goalsTeam2)
    {
        this.owner = owner;
        this.tippedMatch = tippedMatch;
        this.goalsTeam1 = goalsTeam1;
        this.goalsTeam2 = goalsTeam2;
    }

    public MatchTip() {
        goalsTeam1 =
        goalsTeam2 =
        userPoints = -1;
    }

    public User getOwner() {
        return owner;
    }

    public Match getTippedMatch() {
        return tippedMatch;
    }

    public int getGoalsTeam1() {

        return goalsTeam1;
    }

    public int getGoalsTeam2() {
        return goalsTeam2;
    }

    public int getUserPoints() {
        return userPoints;
    }

    public void setGoalsTeam1(int team1Tip) {
        this.goalsTeam1 = team1Tip;
    }

    public void setGoalsTeam2(int team2Tip) {
        this.goalsTeam2 = team2Tip;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setTippedMatch(Match tippedMatch) {
        this.tippedMatch = tippedMatch;
    }
}

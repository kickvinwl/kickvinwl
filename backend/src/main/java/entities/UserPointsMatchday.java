package entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class UserPointsMatchday extends EntityGeneratedKey{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_matchday")
    private Matchday matchday;

    private int points;

    public UserPointsMatchday() {
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Matchday getMatchday() {
        return matchday;
    }

    public void setMatchday(Matchday matchday) {
        this.matchday = matchday;
    }

    public User getuser() {
        return user;
    }

    public void setuser(User user) {
        this.user = user;
    }
}

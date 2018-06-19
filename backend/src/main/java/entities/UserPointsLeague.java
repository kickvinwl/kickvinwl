package entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class UserPointsLeague extends EntityGeneratedKey{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_league")
    private League league;

    private int points;

    public UserPointsLeague() {
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public League getleague() {
        return league;
    }

    public void setleague(League league) {
        this.league = league;
    }

    public User getuser() {
        return user;
    }

    public void setuser(User user) {
        this.user = user;
    }
}

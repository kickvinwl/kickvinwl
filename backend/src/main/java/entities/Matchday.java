package entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Matchday extends EntityGeneratedKey{

	private int matchday;

	private int externalMatchID;

	@ManyToOne
	@JoinColumn(name = "fk_league")
	private League league;

	public Matchday(int matchday) {
		this.matchday = matchday;
	}

	public Matchday() {
	}

	public int getMatchday() {
		return matchday;
	}

	public void setMatchday(int matchday) {
		this.matchday = matchday;
	}

	public int getExternalMatchID() {
		return externalMatchID;
	}

	public void setExternalMatchID(int externalMatchID) {
		this.externalMatchID = externalMatchID;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	@Override
	public String toString() {
		return matchday + "|" + externalMatchID + "|" + league.getId();
	}
}

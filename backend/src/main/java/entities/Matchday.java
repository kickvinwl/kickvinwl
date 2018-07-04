package entities;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Matchday extends EntityGeneratedKey{

	private int matchday;
	private int externalMatchDayID;

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

	public int getExternalMatchDayID() {
		return externalMatchDayID;
	}

	public void setExternalMatchDayID(int externalMatchID) {
		this.externalMatchDayID = externalMatchID;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	@Override
	public String toString() {
		return matchday + "|" + externalMatchDayID + "|" + league.getId();
	}
}

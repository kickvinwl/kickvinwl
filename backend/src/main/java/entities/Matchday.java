package entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

@Entity
public class Matchday extends EntityGeneratedKey{

	private int matchday;

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
}

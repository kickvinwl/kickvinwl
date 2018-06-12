package entities;
import javax.persistence.Entity;

@Entity
public class League extends EntityGeneratedKey {

	private int leagueId;

	private String season;

	private Matchday currentMatchday;

	public int getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(int leagueId) {
		this.leagueId = leagueId;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Matchday getCurrentMatchday() {
		return currentMatchday;
	}

	public void setCurrentMatchday(Matchday currentMatchday) {
		this.currentMatchday = currentMatchday;
	}
}

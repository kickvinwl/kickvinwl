package entities;
import javax.persistence.*;

@Entity
public class League extends EntityGeneratedKey {

	private String leagueId;

	private String season;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="current_Matchday")
	private Matchday currentMatchday;

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
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

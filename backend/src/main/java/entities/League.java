package entities;
import manager.MatchDayManager;
import persistence.LeaguePersistenceService;
import persistence.MatchPersistenceService;
import persistence.MatchdayPersistenceService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//TODO: leagueid und season sind primärschlüssel, aber als solche noch nicht technisch deklariert (EmbeddedKey)
@Entity
public class League extends EntityGeneratedKey {

	private String leagueId;
	private String season;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="current_Matchday")
	private Matchday currentMatchday;

	@OneToMany(mappedBy = "id")
	private List<Matchday> matchdays = new ArrayList();

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

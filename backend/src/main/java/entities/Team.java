package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends EntityGeneratedKey {


	private int teamId;

	private String teamName;
	private String teamIconURL;

	@Column
	@OneToMany(mappedBy = "team")
	private List<Match> home = new ArrayList<>();

	@Column
	@OneToMany(mappedBy = "team2")
	private List<Match> away = new ArrayList<>();

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamIconURL() {
		return teamIconURL;
	}

	public void setTeamIconURL(String teamIconURL) {
		this.teamIconURL = teamIconURL;
	}
}

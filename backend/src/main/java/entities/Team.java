package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends EntityGeneratedKey {

	private int teamId;

	private String teamName;
	private String teamIconURL;

	@JsonIgnore
	@Column
	@OneToMany(mappedBy = "team")
	private List<Match> home = new ArrayList<>();

	@JsonIgnore
	@Column
	@OneToMany(mappedBy = "team2")
	private List<Match> away = new ArrayList<>();

	public Team() {
		teamIconURL = "default";
	}

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

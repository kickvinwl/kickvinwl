package de.mankianer.kickwinvl.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class League{

	@Id
	@GeneratedValue
	private Long leagID;

	private String ext_leagueId;
	private String season;

	@ManyToOne
	private Matchday currentMatchday;

	@OneToMany
	private List<Matchday> matchdays = new ArrayList();
}

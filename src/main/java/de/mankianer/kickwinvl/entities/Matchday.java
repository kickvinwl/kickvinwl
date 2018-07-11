package de.mankianer.kickwinvl.entities;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Matchday{

	@Id
	@GeneratedValue
	private Long matchdayId;

	private int matchday;
	private int externalMatchDayID;

	@ManyToOne
	private League league;

	public Matchday(int matchday) {
		this.matchday = matchday;
	}

	public Matchday() {
	}
}

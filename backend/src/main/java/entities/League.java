package entities;
import javax.persistence.Entity;

@Entity
public class League extends EntityGeneratedKey {
	private int leagueId;
	private String season;  //e.g. 2018
}

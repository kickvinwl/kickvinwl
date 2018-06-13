package entities;
import javax.persistence.Entity;

@Entity
public class Matchday extends EntityGeneratedKey{
	//TODO: Mapping
	private int matchdayId;
	
	//TODO: Mapping
	//FK
	private int leagueId;
}

package entities;
import javax.persistence.Entity;

@Entity
public class Matchday extends EntityGeneratedKey{
	private int matchdayId;
	
	//FK
	private int leagueId;
}

package persistence;

import java.util.ArrayList;
import java.util.List;

import entities.Achievement;
import entities.User;

//Triggered SOMEHOW after ech Matchday     //TODO
public class AchievementsChecker {
	
	private ArrayList<Achievement> achievements ;
	
	public AchievementsChecker() {
		achievements = (ArrayList<Achievement>) AchievementPersistenceService.getInstance().getAll();
	}
	public void check(){
		String query;
		for (Achievement achievement : achievements) {
			query = achievement.getAchievementQuerry();
			List<Integer> userIds = UserPersistenceService.getInstance().getUsersForAchieveQuery(query);
			for (int id : userIds) {
				User u = UserPersistenceService.getInstance().getById(id);
				u.addAchievment(achievement);
				UserPersistenceService.getInstance().save(u);
				// CHECK Primärschlüssel richtig gesetzt?
				//wenn nein, dann prüfen ob ach User Kombi bereits existiert
			}
		}
	}

}

package persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import entities.Achievement;
import entities.User;

//Triggered SOMEHOW after ech Matchday     //TODO
public class AchievementsChecker {

	private ArrayList<Achievement> achievements ;

	public AchievementsChecker() {
		achievements = new ArrayList<Achievement>(AchievementPersistenceService.getInstance().getAll());
	}
	public void check(){
		String query;
		System.out.println(achievements.size());
		for (int i = 0; i < achievements.size(); i++) {

			System.out.println(achievements.get(i).toString());

			for (Achievement achievement : achievements) {
				query = achievement.getAchievementQuerry();
				System.out.println(query);
				List<User> user = UserPersistenceService.getInstance().getUsersForAchieveQuery(query);
				for (User u : user) {
					System.out.println(u.toString());
					//				u.addAchievment(achievement);
					//				UserPersistenceService.getInstance().save(u);
					// CHECK Primärschlüssel richtig gesetzt?
					//wenn nein, dann prüfen ob ach User Kombi bereits existiert
				}
			}
		}

	}

}
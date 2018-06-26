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
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("###################### CHECKER ######################");
		String query;
		System.out.println("Achievements: " + achievements.size());


		for (Achievement achievement : achievements) {
			System.out.print(achievement.getTitle() + ": ");
			query = achievement.getAchievementQuerry();
//			System.out.println(query);
			List<User> user = UserPersistenceService.getInstance().getUsersForAchieveQuery(query);
			System.out.print("("+ user.size() + ") ");
			for (User u : user) {
				System.out.println("*** " + u.getUserName());
				//				u.addAchievment(achievement);
				//				UserPersistenceService.getInstance().save(u);
				// CHECK Primärschlüssel richtig gesetzt?
				//wenn nein, dann prüfen ob ach User Kombi bereits existiert
			}
			System.out.println();
		}
	}

}


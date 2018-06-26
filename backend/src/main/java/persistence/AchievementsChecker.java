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
			query = achievement.getAchievementQuerry();
			//			System.out.println(query);
			List<User> user = UserPersistenceService.getInstance().getUsersForAchieveQuery(query);
			for (User u : user) {
				boolean hasAch = false;
				for(Achievement a : u.getAchievements()) {
					if(a.getId() == achievement.getId())
						hasAch = true;
				}
				if(!hasAch) {
					System.out.println(achievement.getTitle() + " for user " + u.getUserName() + " unlocked");
					u.addAchievment(achievement);
					achievement.addUsers(u);
					UserPersistenceService.getInstance().update(u);
					AchievementPersistenceService.getInstance().update(achievement);
				}
				//				 CHECK Primärschlüssel richtig gesetzt?
				//wenn nein, dann prüfen ob ach User Kombi bereits existiert

			}
		}
	}
}




package persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import entities.Achievement;
import entities.User;

//Triggered SOMEHOW after ech Matchday     //TODO
public class AchievementsChecker {

	private List<Achievement> achievements;

	public AchievementsChecker() {
		try{
			achievements = new ArrayList<>(AchievementPersistenceService.getInstance().getAll());
		}catch(NoResultException e) {
			// Keine Achievements in DB
			achievements = new ArrayList<>();
		}
	}
	public void check(){

		String query;


		for (Achievement achievement : achievements) {
			query = achievement.getAchievementQuery();
			//			System.out.println(query);
			
			List<User> user;
			try {
				user = UserPersistenceService.getInstance().getUsersForAchieveQuery(query);				
			}catch(NoResultException e) {
				//kein Nutzer hat dieses Achievement freigeschaltet
				user= new ArrayList<>();
			}
			for (User u : user) {
				boolean hasAch = false;
				for(Achievement a : u.getAchievements()) {
					if(a.getId() == achievement.getId())
						hasAch = true;
				}
				if(!hasAch) {
					System.out.println(achievement.getTitle() + " for user " + u.getUserName() + " unlocked");
					u.addAchievement(achievement);
					//achievement.addUsers(u);
					UserPersistenceService.getInstance().update(u);
					AchievementPersistenceService.getInstance().update(achievement);
				}
			}
		}
	}
}




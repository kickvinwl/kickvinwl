package persistence;

import entities.Achievement;
import entities.Match;
import entities.MatchTip;
import entities.User;

public class DatabaseDefaultData {

	private static DatabaseDefaultData instance;

	public static DatabaseDefaultData getInstance()
	{
		return instance = instance != null ? instance : new DatabaseDefaultData();
	}
	private DatabaseDefaultData() {};

	public void generatetData(){
		achievementData();
		userDataMitTips();
	}

	private void userDataMitTips()
	{
		System.out.println("WWWWWWWWWWWWWWWWWWWWWWWW");

		UserPersistenceService ups = UserPersistenceService.getInstance();
		MatchTipPersistenceService mtps = MatchTipPersistenceService.getInstance();
		MatchPersistenceService mps = MatchPersistenceService.getInstance();

		if(ups.hasEntries())
			return;

		User user = new User("qwertz", "t");
		User user1 = new User("qwertz1", "t1");
		User user2 = new User("qwertz2", "t2");

		ups.save(user);
		ups.save(user1);
		ups.save(user2);

		//Tipsabgeben
		Match match = new Match();
		mps.save(match);

		MatchTip mt = new MatchTip(user, match, 1, 10);
		mtps.save(mt);
		user.addTip(mt);
//		MatchTip mt2 = new MatchTip(user, match, 10, 3);
//		mtps.save(mt2);
//		user.addTip(mt2);

	}

	private void achievementData() {
		AchievementPersistenceService aps = AchievementPersistenceService.getInstance();
		if(aps.hasEntries())
			return;

		Achievement ach = new Achievement();          
		ach.setTitle("Rookie");
		ach.setAchievementDescription("Sie haben es geschafft sich anzumelden");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Fortuna");
		ach.setAchievementDescription("Erziele einen Punkt");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("I like where this is going");
		ach.setAchievementDescription("Erziele 123 Punkte");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Spartaaaa");
		ach.setAchievementDescription("Erziele 300 Punkte");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("You cant stop me");
		ach.setAchievementDescription("Erziele 600 Punkte");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Profitipper");
		ach.setAchievementDescription("Erziele 1234 Punkte");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Been There� Rocked That");
		ach.setAchievementDescription("Alle Tendenzen an einem Spieltag richtig getippt");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("I knew it");
		ach.setAchievementDescription("Ein perfekt getippter Spieltag");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("A fresh start");
		ach.setAchievementDescription("Ein Spiel richtig getippt");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Look what I can do!");
		ach.setAchievementDescription("Drei Spiele richtig getippt. (an einem Spieltag)");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("A Star is Born!");
		ach.setAchievementDescription("F�nf Spiele richtig getippt. (an einem Spieltag)");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Reach for the stars");
		ach.setAchievementDescription("Einen Spieltag als bester getippt");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Miracles come when you least expect them");
		ach.setAchievementDescription("Spieltag ohne einen einzigen Punkt");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Legend");
		ach.setAchievementDescription("Gewinnen Sie 5 Tippspiele");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Master");
		ach.setAchievementDescription("Gewinnen Sie 3 Tippspiele");
		aps.save(ach);

		ach = new Achievement();   
		ach.setTitle("Tippsielsieger");
		ach.setAchievementDescription("Gewinnen Sie ein Tippspiele");
		aps.save(ach);
	}
}
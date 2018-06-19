package persistence;

import entities.*;

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
//		league();
	}

//	private void league()
//	{
//		LeaguePersistenceService lps = LeaguePersistenceService.getInstance();
//		MatchdayPersistenceService mdps = MatchdayPersistenceService.getInstance();
//
//		League league = new League();
//		league.setSeason("2017/18");
//
//		Matchday mdDefault = new Matchday();
//		mdDefault.setMatchday(28);
//		mdps.save(mdDefault);
//		mdps.setDefault(mdDefault);
//
//		league.setCurrentMatchday(mdDefault);
//
//		lps.save(league);
//	}

	private void userDataMitTips()
	{
		UserPersistenceService ups = UserPersistenceService.getInstance();
		MatchTipPersistenceService mtps = MatchTipPersistenceService.getInstance();
		MatchPersistenceService mps = MatchPersistenceService.getInstance();
		TeamPersistenceService tps = TeamPersistenceService.getInstance();
		MatchdayPersistenceService mdps = MatchdayPersistenceService.getInstance();

		if(ups.hasEntries())
			return;

		Matchday md = new Matchday();
		md.setMatchday(1);
		mdps.save(md);

		md = new Matchday();
		md.setMatchday(27);
		mdps.save(md);


		Team t1 = new Team();
		t1.setTeamName("Team 1");
		t1.setTeamIconURL("default");
		Team t2 = new Team();
		t2.setTeamName("Team 2");
		t2.setTeamIconURL("default");
		tps.save(t1);
		tps.save(t2);

		User user = new User("qwertz", "t");
		User user1 = new User("qwertz1", "t1");
		User user2 = new User("qwertz2", "t2");

		ups.save(user);
		ups.save(user1);
		ups.save(user2);

		Match match = new Match();
		match.setMatchday(md);
		match.setTeam(t1);
		match.setTeam2(t2);
		mps.save(match);

		Match match2 = new Match();
		match2.setTeam(t2);
		match2.setTeam2(t1);
		mps.save(match2);

		MatchTip mt = new MatchTip(user, match, 1, 10);
		mtps.save(mt);
		user.addTip(mt);

		MatchTip mt2 = new MatchTip(user, match2, 2, 3);
		mtps.save(mt2);
		user.addTip(mt2);
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
		ach.setAchievementQuerry("SELECT u.id FROM User u");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Fortuna");
		ach.setAchievementDescription("Erziele einen Punkt");
		ach.setAchievementQuerry("SELECT mt.fk_user FROM matchtip mt GROUP BY fk_user HAVIN COUNT(mt.userPoints) > 1");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("I like where this is going");
		ach.setAchievementDescription("Erziele 123 Punkte");
		ach.setAchievementQuerry("SELECT mt.fk_user FROM matchtip mt GROUP BY fk_user HAVIN COUNT(mt.userPoints) > 123");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Spartaaaa");
		ach.setAchievementDescription("Erziele 300 Punkte");
		ach.setAchievementQuerry("SELECT mt.fk_user FROM matchtip mt GROUP BY fk_user HAVIN COUNT(mt.userPoints) > 300");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("You cant stop me");
		ach.setAchievementDescription("Erziele 600 Punkte");
		ach.setAchievementQuerry("SELECT mt.fk_user FROM matchtip mt GROUP BY fk_user HAVIN COUNT(mt.userPoints) > 600");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Profitipper");
		ach.setAchievementDescription("Erziele 1234 Punkte");
		ach.setAchievementQuerry("SELECT mt.fk_user FROM matchtip mt GROUP BY fk_user HAVIN COUNT(mt.userPoints) > 1234");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Been There, Rocked That");
		ach.setAchievementDescription("Alle Tendenzen an einem Spieltag richtig getippt");
		ach.setAchievementQuerry("xx");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("I knew it");
		ach.setAchievementDescription("Ein perfekt getippter Spieltag");
		ach.setAchievementQuerry("xx");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("A fresh start");
		ach.setAchievementDescription("Ein Spiel richtig getippt");
		ach.setAchievementQuerry("xx");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Look what I can do!");
		ach.setAchievementDescription("Drei Spiele richtig getippt. (an einem Spieltag)");
		ach.setAchievementQuerry("xx");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("A Star is Born!");
		ach.setAchievementDescription("F�nf Spiele richtig getippt. (an einem Spieltag)");
		ach.setAchievementQuerry("xx");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Reach for the stars");
		ach.setAchievementDescription("Einen Spieltag als bester getippt");
		ach.setAchievementQuerry("xx");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Miracles come when you least expect them");
		ach.setAchievementDescription("Spieltag ohne einen einzigen Punkt");
		ach.setAchievementQuerry("xx");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Legend");
		ach.setAchievementDescription("Gewinnen Sie 5 Tippspiele");
		ach.setAchievementQuerry("xx");
		aps.save(ach);

		ach = new Achievement();
		ach.setTitle("Master");
		ach.setAchievementDescription("Gewinnen Sie 3 Tippspiele");
		ach.setAchievementQuerry("xx");
		aps.save(ach);

		ach = new Achievement();   
		ach.setTitle("Tippsielsieger");
		ach.setAchievementDescription("Gewinnen Sie ein Tippspiele");
		ach.setAchievementQuerry("xx");
		aps.save(ach);
	}
}
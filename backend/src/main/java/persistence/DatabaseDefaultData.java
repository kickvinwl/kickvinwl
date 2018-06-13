package persistence;


import entities.Achievement;
import entities.League;
import manager.BundesligaTableManager;

public class DatabaseDefaultData {

    private static DatabaseDefaultData instance;

    public static DatabaseDefaultData getInstance() {
        return instance = instance != null ? instance : new DatabaseDefaultData();
    }

    private DatabaseDefaultData() {
    }

    ;

    public void generatetData() {
        achievementData();
        generateLeague();
        generateBundesligaTable();
    }

    private void achievementData() {
        AchievementPersistenceService aps = AchievementPersistenceService.getInstance();
        if (aps.hasEntries())
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

    private void generateLeague() {
        League l = new League();
        l.setLeagueId("bl1");
        l.setSeason("2017");
        LeaguePersistenceService.getInstance().save(l);
    }

    private void generateBundesligaTable() {
        BundesligaTableManager blmanager = new BundesligaTableManager(
                LeaguePersistenceService.getInstance().getCurrentLeague());
        try {
            blmanager.updateData();
            System.out.println("SUCCESS: BUNDESLIGATABLE LOADED");
        } catch (Exception e) {
            System.out.println("ERROR: BUNDESLIGATABLE-GENERATION");
        }
    }
}

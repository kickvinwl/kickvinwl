package persistence;

import entities.Achievement;

public class AchievementTestData {

    public static void generateTestData(){
        AchievementPersistenceService aps = AchievementPersistenceService.getInstance();

        Achievement ach = new Achievement();
        ach.setTitle("Rookie");
        ach.setAchievementName("Anmeldung erfolgreich!");
        ach.setAchievementDescription("Sie haben es geschafft, sich anzumelden!");
        aps.save(ach);

        ach = new Achievement();
        ach.setTitle("Fortuna");
        ach.setAchievementName("Erster Tipp!");
        ach.setAchievementDescription("Sie haben es geschafft, einen Tipp abzugeben!");
        aps.save(ach);
    }
}
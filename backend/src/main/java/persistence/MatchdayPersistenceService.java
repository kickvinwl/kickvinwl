package persistence;

import entities.Matchday;

public class MatchdayPersistenceService  extends PersistenceService<Matchday> {

    private static MatchdayPersistenceService instance;

    public static MatchdayPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new MatchdayPersistenceService();
    }
    private MatchdayPersistenceService() {};
}

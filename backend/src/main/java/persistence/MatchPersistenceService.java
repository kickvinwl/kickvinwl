package persistence;

import entities.Match;

public class MatchPersistenceService extends PersistenceService<Match> {

    private static MatchPersistenceService instance;

    public static MatchPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new MatchPersistenceService();
    }
    private MatchPersistenceService() {};
}

package persistence;

import entities.MatchTip;

public class MatchTipPersistenceService extends PersistenceService<MatchTip> {

    private static MatchTipPersistenceService instance;

    public static MatchTipPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new MatchTipPersistenceService();
    }
    private MatchTipPersistenceService() {};

}

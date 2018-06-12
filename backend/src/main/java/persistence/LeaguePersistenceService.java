package persistence;

import entities.League;

/**
 *
 */
public class LeaguePersistenceService extends PersistenceService<League> {

    private static LeaguePersistenceService instance;

    /**
     *
     * @return
     */
    public static LeaguePersistenceService getInstance()
    {
        return instance = instance != null ? instance : new LeaguePersistenceService();
    }

}


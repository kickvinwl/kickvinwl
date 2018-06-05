package persistence;

import entities.Team;

public class TeamPersistenceService extends PersistenceService<Team> {

    private static TeamPersistenceService instance;

    public static TeamPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new TeamPersistenceService();
    }
    private TeamPersistenceService() {};

}

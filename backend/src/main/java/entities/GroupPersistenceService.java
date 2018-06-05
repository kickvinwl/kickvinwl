package entities;

import persistence.PersistenceService;

public class GroupPersistenceService extends PersistenceService<Group>{
	
    private static GroupPersistenceService instance;

    public static GroupPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new GroupPersistenceService();
    }
    private GroupPersistenceService() {};
	
}

package persistence;

import entities.NewsfeedMessage;

public class NewsfeedPersistenceService extends PersistenceService<NewsfeedMessage>{

	   private static NewsfeedPersistenceService instance;

	public static NewsfeedPersistenceService getInstance() {
	        return instance = instance != null ? instance : new NewsfeedPersistenceService();
	    }
}

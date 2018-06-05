package persistence;

import java.util.ArrayList;

import entities.Group;
import entities.User;
import resources.SearchResult;

public class SearchPersistenceService {
	
	private static SearchPersistenceService instance;

    public static SearchPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new SearchPersistenceService();
    }
    private SearchPersistenceService() {};
    
    public SearchResult getResults(String searchtext) {
    	SearchResult result = new SearchResult();
    	result.setUsers(new ArrayList<User>(UserPersistenceService.getInstance().getSearch(searchtext)));
    	result.setGroups(new ArrayList<Group>(GroupPersistenceService.getInstance().getSearch(searchtext)));
    	return result;
    }

}

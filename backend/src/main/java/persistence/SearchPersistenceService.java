package persistence;

import java.util.ArrayList;

import entities.Group;
import entities.User;
import persistence.exceptions.NoSearchResultException;
import resources.datamodel.SearchResult;

import javax.persistence.NoResultException;

/**
 * Alle Datenbank-Funktionen für die zugehörige Datenklasse
 */
public class SearchPersistenceService {
	
	private static SearchPersistenceService instance;

    public static SearchPersistenceService getInstance()
    {
        return instance = instance != null ? instance : new SearchPersistenceService();
    }
    private SearchPersistenceService() {};
    
    public SearchResult getResults(String searchtext) throws NoSearchResultException {
    	SearchResult result = new SearchResult();
    	boolean user = true;
    	boolean group = true;
    	try {
            result.setGroups(new ArrayList<Group>(GroupPersistenceService.getInstance().getSearch(searchtext)));
        }
        catch (NoResultException exception)
        {
            group = false;
        }

        try {
            result.setUsers(new ArrayList<User>(UserPersistenceService.getInstance().getSearch(searchtext)));

        }
        catch (NoResultException exception)
        {
            user = false;
        }

        if(user == false && group == false)
            throw new NoSearchResultException();
    	return result;
    }

}

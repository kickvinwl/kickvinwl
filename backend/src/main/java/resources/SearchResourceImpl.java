package resources;

import entities.User;
import persistence.SearchPersistenceService;
import persistence.UserPersistenceService;
import resources.datamodel.SearchResult;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;

public class SearchResourceImpl extends SearchResource {


    @Override
    public Response searchForParameters(String token, String searchString) {
        response = Response.accepted().build();

        try {
            UserPersistenceService.getInstance().getBySessionKey(token);
            searchResult = SearchPersistenceService.getInstance().getResults(searchString);
            response = Response.accepted(searchResult).build();
        }
        catch (SecurityException | NoResultException exception) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return response;
    }
}

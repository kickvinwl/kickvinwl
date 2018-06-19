package resources;

import entities.User;
import persistence.SearchPersistenceService;
import persistence.UserPersistenceService;
import persistence.exceptions.NoSearchResultException;
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
        catch (NoSearchResultException exception) {
            response = Response.status(Response.Status.fromStatusCode(600)).build();
        }

        return response;
    }
}

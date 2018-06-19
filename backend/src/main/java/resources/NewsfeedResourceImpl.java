package resources;

import entities.NewsfeedMessage;
import persistence.NewsfeedPersistenceService;
import persistence.UserPersistenceService;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import java.util.List;

public class NewsfeedResourceImpl extends NewsfeedResource{

    @Override
    public Response getNews(String token) {
        response = Response.accepted().build();
        try{
            //UserPersistenceService.getInstance().getBySessionKey(token);
            List<NewsfeedMessage> news = NewsfeedPersistenceService.getInstance().getValidNewsfeedMessages();
            response = Response.accepted(news.get(0)).build();
        } catch ( NoResultException e1) {
            response = Response.status(Response.Status.fromStatusCode(600)).build();
        }

        return response;
    }
}

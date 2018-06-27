package resources;

import entities.User;
import manager.NewsfeedManager;
import persistence.UserPersistenceService;

import javax.persistence.NoResultException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.Map;

public class NewsfeedResourceImpl extends NewsfeedResource {

    @Override
    public Response getNews(String token) {
        response = Response.accepted().build();
        System.out.println("TOKEN = " + token);
        try {
            UserPersistenceService.getInstance().getBySessionKey(token);
            NewsfeedManager newsManager = new NewsfeedManager();
            response = Response.accepted(newsManager.getValidNews()).build();
        } catch (NoResultException e1) {
            response = Response.status(Response.Status.NO_CONTENT).build();
        } catch (SecurityException e2) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return response;
    }

    @Override
    public Response createNews(Map<String, String> input) {
        response = Response.status(Response.Status.OK).build();
        String token = input.get("token");
        try {
            User user = UserPersistenceService.getInstance().getBySessionKey(token);
            NewsfeedManager newsManager = new NewsfeedManager();
            newsManager.createNewsMessage(input, user);
        } catch (NoResultException |SecurityException ex1) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (ParseException ex2) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return response;
    }

    @Override
    public Response deleteNews(Map<String, String> input, int newsId) {
        response = Response.status(Response.Status.OK).build();
        String token = input.get("token");
        try {
            User user = UserPersistenceService.getInstance().getBySessionKey(token);
            NewsfeedManager newsManager = new NewsfeedManager();
            newsManager.deleteNewsMessage(newsId, user);
        } catch (NotFoundException ex) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        } catch (NoResultException | SecurityException ex2) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return response;
    }
}

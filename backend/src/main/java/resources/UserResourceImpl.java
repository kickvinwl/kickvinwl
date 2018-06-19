package resources;

import entities.Achievement;
import entities.User;
import persistence.UserPersistenceService;

import javax.persistence.NoResultException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class UserResourceImpl extends UserResource {

    @Override
    public Response setImage(String token, byte[] image) {
        response = Response.accepted().build();
        User user;
        try {
            user = UserPersistenceService.getInstance().getBySessionKey(token);
            user.setUserPicture(image);
            UserPersistenceService.getInstance().update(user);
        }
        catch (SecurityException | NoResultException exception) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.accepted().build();
    }

    @Override
    public Response setAchievment(String token, Achievement achievement) {
        response = Response.accepted().build();
        User user;
        try {
            user = UserPersistenceService.getInstance().getBySessionKey(token);
            // TODO: Achievment setzen
            UserPersistenceService.getInstance().update(user);
        }
        catch (SecurityException | NoResultException exception) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.accepted().build();
    }

    @Override
    public Response getUserByToken(String token) {
        response = Response.accepted().build();

        try {
            UserPersistenceService.getInstance().getBySessionKey(token);

            User user = UserPersistenceService.getInstance().getBySessionKey(token);

            response = Response.accepted(user).build();
        }
        catch (SecurityException | NoResultException exception) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return response;
    }

    @Override
    public Response getUserByName(String token, String userName) {
        response = Response.accepted().build();

        try {
            UserPersistenceService.getInstance().getBySessionKey(token);

            User user = UserPersistenceService.getInstance().getByName(userName);

            response = Response.accepted(user).build();
        }
        catch (SecurityException | NoResultException exception) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return response;
    }



    @Override
    public Response removeSessionKey(String token) {
        Response.ResponseBuilder response = Response.accepted();
        try {
            User user = UserPersistenceService.getInstance().getBySessionKey(token);
            user.setSessionKey("");
            UserPersistenceService.getInstance().update(user);
        }
        catch (Exception exception) {
            response.status(Response.Status.UNAUTHORIZED);
        }
        return response.build();
    }
}

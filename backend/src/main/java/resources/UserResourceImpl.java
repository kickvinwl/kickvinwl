package resources;

import entities.User;
import persistence.UserPersistenceService;

import javax.persistence.NoResultException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class UserResourceImpl extends UserResource {



    @Override
    public Response setUser(User user) {
        Response response = Response.accepted().build();
        try {
            UserPersistenceService.getInstance().getBySessionKey(user.getSessionKey());

            UserPersistenceService.getInstance().update(user);
        }
        catch (SecurityException | NoResultException exception) {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        userPersistenceService.update(user);
        return Response.accepted().build();
    }

    @Override
    public Response getUserByToken(String token) {
        Response.ResponseBuilder rb = Response.accepted();
        //User zu sessionKey finden
        User user = UserPersistenceService.getInstance().getBySessionKey(token);
        if(user != null) {
            //Token prüfen
            //TODO prüfen ob sessionKey noch Gültig
            //TODO User.Lastlogin < AktuelleZeit + 30 min
            if (true /*Token ist Gültig*/) {
                rb.entity(user);
            } else {
                rb.status(Response.Status.UNAUTHORIZED);
            }
        }
        else
        {
            rb.status(Response.Status.BAD_REQUEST);
        }

        return rb.build();
    }

    @Override
    public Response getUserByName(String token, String userName) {
        Response response = Response.accepted().build();

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

package resources;

import entities.User;
import persistence.UserPersistenceService;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public class UserResourceImpl extends UserResource {

    @Override
    public Response getUser(String token) {
        Response.ResponseBuilder rb = Response.accepted();

        // return userPersistenceService.get(token);
        //if token valid
        User user = new User();
        user.setUserName("Fritz");
        user.setUserIsAdmin(true);
        rb.entity(user);

        //else
        // rb.status(Response.Status.UNAUTHORIZED);
        return rb.build();
    }

    @Override
    public Response setUser(User user) {
        userPersistenceService.update(user);
        return Response.accepted().build();
    }

    @Override
    public Response getUserBySessionKey(String sessionKey) {

        Response.ResponseBuilder rb = Response.accepted();

        //User zu sessionKey finden
        User user = UserPersistenceService.getInstance().getBySessionKey(sessionKey);

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
    public Response removeSessionKey(String token) {
        Response.ResponseBuilder response = Response.accepted();
        try {
            User user = UserPersistenceService.getInstance().getBySessionKey(token);
            user.setSessionKey("");
            UserPersistenceService.getInstance().update(user);
        }
        catch (Exception exception) {
            response.status(Response.Status.BAD_REQUEST);
        }
        return response.build();
    }
}